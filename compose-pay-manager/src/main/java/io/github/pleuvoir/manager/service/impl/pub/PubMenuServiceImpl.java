package io.github.pleuvoir.manager.service.impl.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.github.pleuvoir.manager.common.enums.MenuUser;
import io.github.pleuvoir.manager.common.extension.SequenceGenerator;
import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.common.util.CollectionUtil;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.dao.pub.PubMenuDao;
import io.github.pleuvoir.manager.dao.pub.PubRoleMenuDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubMenuFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubMenuPO;
import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;
import io.github.pleuvoir.manager.model.po.pub.PubRoleMenuPO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuListVO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuView;
import io.github.pleuvoir.manager.service.pub.PubMenuService;
import io.github.pleuvoir.manager.service.pub.PubPermissionsService;
import io.github.pleuvoir.manager.service.pub.PubRoleService;

/**
 * 登录菜单
 * @author LaoShu
 *
 */
@Service("pubMenuService")
public class PubMenuServiceImpl implements PubMenuService {
	
	@Autowired
	private PubMenuDao menuDao;
	@Autowired
	private PubRoleService roleService;
	@Autowired
	private PubRoleMenuDao roleMenuDao;
	@Autowired
	private PubPermissionsService permissionsService;

	/**
	 * 列表查询
	 * @param dto
	 * @return
	 */
	@Override
	public PubMenuListVO queryList(PubMenuFormDTO dto) {
		PageCondition pageCondition = PageCondition.create(dto);
		if(StringUtils.isNotBlank(dto.getTitle())) {
			dto.setTitle("%".concat(dto.getTitle()).concat("%"));
		}
		List<PubMenuPO> list = menuDao.find(pageCondition, dto);
		PubMenuListVO vo = new PubMenuListVO(pageCondition);
		vo.setRows(list);
		return vo;
	}

	/**
	 * 通过id获取菜单
	 * @param id
	 * @return
	 */
	@Override
	public PubMenuPO getById(String id) {
		return menuDao.selectById(id);
	}

	/**
	 * 保存
	 * @param po
	 * @throws BusinessException
	 */
	@Override
	public void save(PubMenuPO po) throws BusinessException {
		//0级菜单
		PubMenuPO zeroMenu = this.findMenuNodeZeroByUsername(SessionUtil.getUserName()).get(0);
		po.setId("U" + SequenceGenerator.next("SEQ_MENU_ID"));
		po.setHasChild(PubMenuPO.HASCHILE_FALSE);
		po.setNode(PubMenuPO.NODE_ONE);
		po.setParentId(zeroMenu.getId());
		po.setSort(menuDao.getNextSort(zeroMenu.getId()));
		po.setIsShow(PubMenuPO.IS_SHOW_TRUE);
		Integer in = menuDao.insert(po);
		AssertUtil.assertOne(in, "保存菜单失败");
		
		//用户拥有的角色
		List<PubRolePO> roleList = roleService.findRolesByUsername(SessionUtil.getUserName());
		for(PubRolePO rolePo : roleList) {
			PubRoleMenuPO roleMenuPo = new PubRoleMenuPO();
			roleMenuPo.setMenuId(po.getId());
			roleMenuPo.setRoleId(rolePo.getId());
			Integer inrm = roleMenuDao.insert(roleMenuPo);
			AssertUtil.assertOne(inrm, "保存角色菜单关联关系失败");
		}
	}

	/**
	 * 修改
	 * @param po
	 * @throws BusinessException
	 */
	@Override
	public void update(PubMenuPO po) throws BusinessException {
		Integer up = menuDao.updateById(po);
		AssertUtil.assertOne(up, "修改菜单失败");
	}

	/**
	 * 删除
	 * @param menuId
	 * @throws BusinessException
	 */
	@Override
	public void delete(String menuId) throws BusinessException {
		List<PubPermissionPO> permissionList = permissionsService.findPermissionsByMenuId(menuId);
		if(!CollectionUtil.isEmpty(permissionList)) {
			throw new BusinessException("请先删除该菜单下的操作权限");
		}
		if(this.menuHasChild(menuId)) {
			throw new BusinessException("请先删除该菜单下的子菜单");
		}
		roleMenuDao.deleteByMenuId(menuId);
		Integer de = menuDao.deleteById(menuId);
		AssertUtil.assertOne(de, "删除菜单失败");
	}

	/**
	 * 排序
	 * @param jsonStr
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void sort(String jsonStr) throws BusinessException {
		if(StringUtils.isBlank(jsonStr)) {
			throw new BusinessException("未修改任何菜单");
		}
		List<PubMenuPO> batchUpdateList = new ArrayList<>();
		//0级菜单
		PubMenuPO zeroMenu = this.findMenuNodeZeroByUsername(SessionUtil.getUserName()).get(0);
		JSONArray jarrOne = JSONArray.parseArray(jsonStr);
		//循环一级菜单
		for (int i = 0; i < jarrOne.size(); i++){
			JSONObject jObjOne = jarrOne.getJSONObject(i);
			PubMenuPO menuOne = new PubMenuPO();
			menuOne.setId(jObjOne.get("id").toString());
			menuOne.setNode(PubMenuPO.NODE_ONE);
			menuOne.setParentId(zeroMenu.getId());
			menuOne.setSort(String.valueOf(i));
			//没有二级菜单
			if(jObjOne.get("children")==null) {
				menuOne.setHasChild(PubMenuPO.HASCHILE_FALSE);
				batchUpdateList.add(menuOne);
			}
			//有二级菜单
			else {
				menuOne.setHasChild(PubMenuPO.HASCHILE_TRUE);
				batchUpdateList.add(menuOne);
				JSONArray jarrTwo = JSONArray.parseArray(jObjOne.get("children").toString());
				//循环二级菜单
				for (int j = 0; j < jarrTwo.size(); j++){
					JSONObject jObjTwo = jarrTwo.getJSONObject(j);
					PubMenuPO menuTwo = new PubMenuPO();
					menuTwo.setId(jObjTwo.getString("id").toString());
					menuTwo.setNode(PubMenuPO.NODE_TWO);
					menuTwo.setParentId(menuOne.getId());
					menuTwo.setSort(String.valueOf(j));
					//没有三级菜单
					if(jObjTwo.get("children")==null) {
						menuTwo.setHasChild(PubMenuPO.HASCHILE_FALSE);
						batchUpdateList.add(menuTwo);
					}
					//有三级菜单
					else {
						menuTwo.setHasChild(PubMenuPO.HASCHILE_TRUE);
						batchUpdateList.add(menuTwo);
						JSONArray jarrThree = JSONArray.parseArray(jObjTwo.get("children").toString());
						//循环三级菜单
						for(int x = 0; x <jarrThree.size(); x++) {
							JSONObject jObjThree = jarrThree.getJSONObject(x);
							PubMenuPO menuThree = new PubMenuPO();
							menuThree.setId(jObjThree.getString("id").toString());
							menuThree.setNode(PubMenuPO.NODE_THREE);
							menuThree.setParentId(menuTwo.getId());
							menuThree.setSort(String.valueOf(x));
							//没有四级菜单
							if(jObjThree.get("children")==null) {
								menuThree.setHasChild(PubMenuPO.HASCHILE_FALSE);
								batchUpdateList.add(menuThree);
							}else {
								throw new BusinessException("不支持四级菜单");
							}
						}
					}
				}
			}
		}
		
		for(PubMenuPO po : batchUpdateList) {
			Integer in = menuDao.updateById(po);
			AssertUtil.assertOne(in, "更新菜单失败");
		}
		
	}
	
	/**
	 * 通过用户名获取菜单模型
	 * @param username
	 * @param menuUser
	 * @return
	 */
	@Override
	public List<PubMenuView> menuViewList(String username, MenuUser menuUser) {
		
		List<PubMenuPO> userMenuList = new ArrayList<>();
		
		if(MenuUser.MAIN.equals(menuUser)) {
			userMenuList = menuDao.findMenuByUsername(username);
		}else if(MenuUser.MENU_SERVICE.equals(menuUser)) {
			userMenuList = menuDao.findAllMenuByUsername(username);
		}
		
		if(CollectionUtil.isEmpty(userMenuList)){
			return new ArrayList<PubMenuView>();
		}
		
		//存放菜单等级和菜单的对应关系
		Map<Integer,List<PubMenuPO>> levelMenuMap = new HashMap<>();
		for(PubMenuPO m : userMenuList){
			Integer node = StringUtils.isBlank(m.getNode()) ? 0 : Integer.valueOf(m.getNode());
			if(levelMenuMap.containsKey(node)){
				levelMenuMap.get(node).add(m);
			}else{
				levelMenuMap.put(node, CollectionUtil.ofList(m));
			}
		}
		
		//构建成树形
		List<PubMenuView> level0MenuViews = new ArrayList<>();
		tree(level0MenuViews, levelMenuMap);
		return level0MenuViews;
	}
	
	/**
	 * 用户拥有的0级菜单
	 * @param username
	 * @return
	 */
	@Override
	public List<PubMenuPO> findMenuNodeZeroByUsername(String username) {
		return menuDao.findMenuByUsernameAndNode(username, PubMenuPO.NODE_ZERO);
	}

	/**
	 * 用户拥有的一级菜单
	 * @param username
	 * @return
	 */
	@Override
	public List<PubMenuPO> findMenuNodeOneByUsername(String username) {
		return menuDao.findMenuByUsernameAndNode(username, PubMenuPO.NODE_ONE);
	}

	/**
	 * 用户拥有的二级菜单
	 * @param username
	 * @return
	 */
	@Override
	public List<PubMenuPO> findMenuNodeTwoByUsername(String username) {
		return menuDao.findMenuByUsernameAndNode(username, PubMenuPO.NODE_TWO);
	}

	/**
	 * 用户拥有的三级菜单
	 * @param username
	 * @return
	 */
	@Override
	public List<PubMenuPO> findMenuNodeThreeByUsername(String username) {
		return menuDao.findMenuByUsernameAndNode(username, PubMenuPO.NODE_THREE);
	}

	/**
	 * 构建成树形视图
	 * @param menuViews 存放树的顶
	 * @param levelMenus 节点等级与菜单的关系，key是节点等级，value是菜单
	 */
	private void tree(List<PubMenuView> menuViews, Map<Integer,List<PubMenuPO>> levelMenus){
		
		List<PubMenuPO> level0MenuList = levelMenus.get(0);
		//第0级
		for(PubMenuPO level0Menu : level0MenuList){
			PubMenuView level0MenuView = new PubMenuView();
			level0MenuView.setMenu(level0Menu);
			menuViews.add(level0MenuView);
			
			nextLevelMenu(levelMenus, level0MenuView, level0Menu);
		}
	}
	
	/**
	 * 寻找并添加子节点
	 * @param levelMenus 节点等级与菜单的关系，key是节点等级，value是菜单
	 * @param menuView 
	 * @param parentMenu
	 */
	private void nextLevelMenu(Map<Integer,List<PubMenuPO>> levelMenus, PubMenuView menuView, PubMenuPO parentMenu){
		int parentNode = StringUtils.isBlank(parentMenu.getNode()) ? 0 : Integer.parseInt(parentMenu.getNode());
		//下一级
		List<PubMenuPO> levels = levelMenus.get(parentNode + 1);
		if(CollectionUtil.isEmpty(levels)){
			return;
		}
		for(PubMenuPO childMenu : levels){
			if(StringUtils.equals(childMenu.getParentId(), parentMenu.getId())){
				PubMenuView childMenuView = new PubMenuView();
				childMenuView.setMenu(childMenu);
				
				if(menuView.getChildren()==null){
					menuView.setChildren(CollectionUtil.ofList(childMenuView));
				}else{
					menuView.getChildren().add(childMenuView);
				}
				
				nextLevelMenu(levelMenus, childMenuView, childMenu);
			}
		}
	}

	/**
	 * 菜单是否存在子菜单
	 * @param menuId
	 * @return
	 */
	@Override
	public Boolean menuHasChild(String menuId) {
		return menuDao.countByParentMenuId(menuId)>0;
	}

}
