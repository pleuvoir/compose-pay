package io.github.pleuvoir.manager.service.impl.pub;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.common.util.Generator;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.dao.pub.PubRoleDao;
import io.github.pleuvoir.manager.dao.pub.PubRoleMenuDao;
import io.github.pleuvoir.manager.dao.pub.PubRolePermissionDao;
import io.github.pleuvoir.manager.dao.pub.PubUserRoleDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubPermissionFormDTO;
import io.github.pleuvoir.manager.model.dto.pub.PubRoleFormDTO;
import io.github.pleuvoir.manager.model.po.pub.*;
import io.github.pleuvoir.manager.model.vo.pub.PubRoleListVO;
import io.github.pleuvoir.manager.service.pub.PubMenuService;
import io.github.pleuvoir.manager.service.pub.PubPermissionsService;
import io.github.pleuvoir.manager.service.pub.PubRoleService;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 角色
 * @author LaoShu
 *
 */
@Service("pubRoleService")
public class PubRoleServiceImpl implements PubRoleService {
	
	private static final String COLON = ":";
	private static final String HORIZONTAL = "-";
	
	@Autowired
	private PubRoleDao roleDao;
	@Autowired
	private PubUserRoleDao userRoleDao;
	@Autowired
	private PubRoleMenuDao roleMenuDao;
	@Autowired
	private PubRolePermissionDao rolePermissionDao;
	@Autowired
	private PubPermissionsService permissionsService;
	@Autowired
	private PubMenuService menuService;

	/**
	 * 用户拥有的角色
	 * @param username
	 * @return id集合
	 */
	@Override
	public List<PubRolePO> findRolesByUsername(String username) {
		return roleDao.findRolesByUsername(username);
	}

	/**
	 * 用户创建的角色
	 * @param createBy
	 * @return
	 */
	@Override
	public List<PubRolePO> findRliesByCreateBy(String createBy) {
		return roleDao.findRliesByCreateBy(createBy);
	}

	/**
	 * 列表查询
	 * @param form
	 * @return
	 */
	@Override
	public PubRoleListVO queryList(PubRoleFormDTO form) {
		String username = SessionUtil.getUserName();
		form.setLoginUserName(username);
		
		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getName())) {
			form.setName("%".concat(form.getName()).concat("%"));
		}
		
		List<PubRolePO> roleList = roleDao.find(pageCondition, form);
		PubRoleListVO vo = new PubRoleListVO(pageCondition);
		vo.setRows(roleList);
		return vo;
	}

	/**
	 * 保存角色
	 * @param po
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void save(PubRolePO po) throws BusinessException {
		po.setId(Generator.nextUUID());
		po.setCreateBy(SessionUtil.getUserName());
		po.setCreateTime(LocalDateTime.now());
		po.setIsTmp("N");
		Integer in = roleDao.insert(po);
		AssertUtil.assertOne(in, "保存角色失败");
	}

	/**
	 * 修改角色
	 * @param po
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void edit(PubRolePO po) throws BusinessException {
		Integer in = roleDao.updateById(po);
		AssertUtil.assertOne(in, "修改角色失败");
	}

	/**
	 * 删除角色
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void delete(String id) throws BusinessException {
		if(userRoleDao.countByRoleId(id)>0) {
			throw new BusinessException("存在用户使用该角色，无法删除");
		}
		//删除角色菜单关联关系
		roleMenuDao.deleteByRoleId(id);
		//删除角色权限关联关系
		rolePermissionDao.deleteByRoleId(id);
		//删除角色
		Integer de = roleDao.deleteById(id);
		AssertUtil.assertOne(de, "删除角色失败");
	}

	/**
	 * 通过id获取角色
	 * @param id
	 * @return
	 */
	@Override
	public PubRolePO getById(String id) {
		PubRolePO po = new PubRolePO();
		po.setId(id);
		return roleDao.selectById(po);
	}

	/**
	 * 生成树
	 * @param id 角色id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getPermissionTree(String id) {
		List<Map<String,Object>> finalList = new ArrayList<Map<String,Object>>();
		
		String loginUsername = SessionUtil.getUserName();
		
		//登录用户拥有的0级菜单
		List<PubMenuPO> allMenuZeroList = menuService.findMenuNodeZeroByUsername(loginUsername);
		//登录用户拥有的一级菜单
		List<PubMenuPO> allMenuOneList = menuService.findMenuNodeOneByUsername(loginUsername);
		//登录用户拥有的二级菜单
		List<PubMenuPO> allMenuTwoList = menuService.findMenuNodeTwoByUsername(loginUsername);
		//登录用户拥有的三级菜单
		List<PubMenuPO> allMenuThreeList = menuService.findMenuNodeThreeByUsername(loginUsername);
		//登录用户拥有的所有权限
		List<PubPermissionPO> allPermissionList = permissionsService.findPermissionsByUsername(loginUsername);
		//角色拥有的菜单
		List<String> myMenuList = roleMenuDao.findMenuIdByRoleId(id);
		//角色拥有的权限
		List<String> myPermissionList = rolePermissionDao.findPermissionIdByRoleId(id);
		
		//循环0级菜单
		for(PubMenuPO menuZero : allMenuZeroList) {
			Map<String,Object> menuZeroMap = new HashMap<String,Object>();
			menuZeroMap.put("name",menuZero.getTitle());
			menuZeroMap.put("value",menuZero.getId());
			if(myMenuList.contains(menuZero.getId())) {
				menuZeroMap.put("checked","true");
			}
			finalList.add(menuZeroMap);
			//循环一级菜单
			for(PubMenuPO menuOne : allMenuOneList) {
				if(!StringUtils.equals(menuOne.getParentId(), menuZero.getId())) {
					continue;
				}
				List<Map<String,Object>> menuOneMapList = (List<Map<String,Object>>)menuZeroMap.get("children");
				if(menuOneMapList==null) {
					menuOneMapList = new ArrayList<Map<String,Object>>();
					menuZeroMap.put("children", menuOneMapList);
				}
				Map<String,Object> menuOneMap = new HashMap<String,Object>();
				menuOneMap.put("name",menuOne.getTitle());
				menuOneMap.put("value",menuOne.getId());
				if(myMenuList.contains(menuOne.getId())) {
					menuOneMap.put("checked","true");
				}
				menuOneMapList.add(menuOneMap);
				//是否有子菜单
				if(StringUtils.equals("Y", menuOne.getHasChild())) {
					//循环二级菜单
					for(PubMenuPO menuTwo : allMenuTwoList) {
						if(!StringUtils.equals(menuTwo.getParentId(), menuOne.getId())) {
							continue;
						}
						List<Map<String,Object>> menuTwoMapList = (List<Map<String,Object>>)menuOneMap.get("children");
						if(menuTwoMapList==null) {
							menuTwoMapList = new ArrayList<Map<String,Object>>();
							menuOneMap.put("children", menuTwoMapList);
						}
						Map<String,Object> menuTwoMap = new HashMap<String,Object>();
						menuTwoMap.put("name",menuTwo.getTitle());
						menuTwoMap.put("value",menuTwo.getId());
						if(myMenuList.contains(menuTwo.getId())) {
							menuTwoMap.put("checked","true");
						}
						menuTwoMapList.add(menuTwoMap);
						//是否有子菜单
						if(StringUtils.equals("Y", menuTwo.getHasChild())) {
							//循环三级菜单
							for(PubMenuPO menuThree : allMenuThreeList) {
								if(!StringUtils.equals(menuThree.getParentId(), menuTwo.getId())) {
									continue;
								}
								List<Map<String,Object>> menuThreeMapList = (List<Map<String,Object>>)menuTwoMap.get("children");
								if(menuThreeMapList==null) {
									menuThreeMapList = new ArrayList<Map<String,Object>>();
									menuTwoMap.put("children", menuThreeMapList);
								}
								Map<String,Object> menuThreeMap = new HashMap<String,Object>();
								menuThreeMap.put("name",menuThree.getTitle());
								menuThreeMap.put("value",menuThree.getId());
								if(myMenuList.contains(menuThree.getId())) {
									menuThreeMap.put("checked","true");
								}
								menuThreeMapList.add(menuThreeMap);
								//三级菜单权限
								for(PubPermissionPO perThree : allPermissionList) {
									if(!StringUtils.equals(perThree.getMenuId(), menuThree.getId())) {
										continue;
									}
									List<Map<String,Object>> perThreeMapList = (List<Map<String,Object>>)menuThreeMap.get("children");
									if(perThreeMapList==null) {
										perThreeMapList = new ArrayList<Map<String,Object>>();
										menuThreeMap.put("children", perThreeMapList);
									}
									Map<String,Object> perThreeMap = new HashMap<String,Object>();
									perThreeMap.put("name",perThree.getName());
									perThreeMap.put("value",menuZero.getId()+COLON+menuOne.getId()+COLON+menuTwo.getId()+COLON+menuThree.getId()+HORIZONTAL+perThree.getId());
									if(myPermissionList.contains(perThree.getId())) {
										perThreeMap.put("checked","true");
									}
									perThreeMapList.add(perThreeMap);
								}
							}
						}else {
							//二级菜单权限
							for(PubPermissionPO perTwo : allPermissionList) {
								if(!StringUtils.equals(perTwo.getMenuId(), menuTwo.getId())) {
									continue;
								}
								List<Map<String,Object>> perTowMapList = (List<Map<String,Object>>)menuTwoMap.get("children");
								if(perTowMapList==null) {
									perTowMapList = new ArrayList<Map<String,Object>>();
									menuTwoMap.put("children", perTowMapList);
								}
								Map<String,Object> perTwoMap = new HashMap<String,Object>();
								perTwoMap.put("name",perTwo.getName());
								perTwoMap.put("value",menuZero.getId()+COLON+menuOne.getId()+COLON+menuTwo.getId()+HORIZONTAL+perTwo.getId());
								if(myPermissionList.contains(perTwo.getId())) {
									perTwoMap.put("checked","true");
								}
								perTowMapList.add(perTwoMap);
							}
						}
					}
				}else {
					//一级菜单权限
					for(PubPermissionPO perOne : allPermissionList) {
						if(!StringUtils.equals(perOne.getMenuId(), menuOne.getId())) {
							continue;
						}
						List<Map<String,Object>> perOneMapList = (List<Map<String,Object>>)menuOneMap.get("children");
						if(perOneMapList==null) {
							perOneMapList = new ArrayList<Map<String,Object>>();
							menuOneMap.put("children", perOneMapList);
						}
						Map<String,Object> perOneMap = new HashMap<String,Object>();
						perOneMap.put("name",perOne.getName());
						perOneMap.put("value",menuZero.getId()+COLON+menuOne.getId()+HORIZONTAL+perOne.getId());
						if(myPermissionList.contains(perOne.getId())) {
							perOneMap.put("checked","true");
						}
						perOneMapList.add(perOneMap);
					}
				}
			}
		}
		
		return finalList;
	}

	/**
	 * 权限分配保存
	 * @param dto
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void permissionSave(PubPermissionFormDTO dto) throws BusinessException {
		if(StringUtils.isBlank(dto.getId())) {
			throw new BusinessException("角色id空");
		}
		//U002,U007,U008,U002:U007:U008-U00802
		//U009,U009-U00902,U009-U00901
		String[] strCheck = {};
		if(StringUtils.isNotBlank(dto.getStrCHeck())) {
			strCheck = dto.getStrCHeck().split(",");
		}
		Map<String, Object> formMenuMap = new HashMap<>();			//页面传入的menuId        	key:menuId  value:null  
		Map<String, Object> formPermissionMap = new HashMap<>();	//页面传入的permissionId     key:permissionId  value:null  
		for(String strC : strCheck) {
			if(StringUtils.indexOf(strC, HORIZONTAL)!=-1) {
				//U002:U007:U008-U00802
				String[] strMP = strC.split(HORIZONTAL);
				formPermissionMap.put(strMP[1], null);	//装载权限id
				String[] strM = strMP[0].split(COLON);
				for(String strm : strM) {
					formMenuMap.put(strm, null);		//装载菜单id
				}
			}else {
				String[] strM = strC.split(COLON);
				for(String strm : strM) {
					formMenuMap.put(strm, null);		//装载菜单id
				}
			}
		}
		
		//更新角色权限关联关系
		updateRolePermission(dto.getId(), formPermissionMap);
		//更新角色菜单关联关系
		updateRoleMenu(dto.getId(), formMenuMap);
	}
	
	/*
	 * 更新角色权限关联关系
	 */
	private void updateRolePermission(String roleId,Map<String, Object> formPermissionMap) throws BusinessException {
		//角色拥有的权限
		List<String> myPermissionList = rolePermissionDao.findPermissionIdByRoleId(roleId);
		Map<String, Object> myPermissionMap = new HashMap<>();	//key:permissionId   value:null
		for(String sp : myPermissionList) {
			myPermissionMap.put(sp, null);
		}
		List<String> sameKeyList = new ArrayList<>();
		for(Map.Entry<String, Object> formPermissionEntry : formPermissionMap.entrySet()) {
			for(Map.Entry<String, Object> myMenuEntry : myPermissionMap.entrySet()) {
				if(StringUtils.equals(formPermissionEntry.getKey(), myMenuEntry.getKey())) {
					sameKeyList.add(formPermissionEntry.getKey());
				}
			}
		}
		for(String s : sameKeyList) {
			formPermissionMap.remove(s);
			myPermissionMap.remove(s);
		}
		for(Map.Entry<String, Object> myMenuEntry : myPermissionMap.entrySet()) {
			int de = rolePermissionDao.deleteByRoleIdAndPermissionId(roleId, myMenuEntry.getKey());
			AssertUtil.assertOne(de, "删除角色权限关联关系失败");
		}
		for(Map.Entry<String, Object> formPermissionEntry : formPermissionMap.entrySet()) {
			PubRolePermissionPO po = new PubRolePermissionPO();
			po.setRoleId(roleId);
			po.setPermissionId(formPermissionEntry.getKey());
			Integer in = rolePermissionDao.insert(po);
			AssertUtil.assertOne(in, "插入角色权限关联关系失败");
		}
	}
	
	/*
	 * 更新角色菜单关联关系
	 */
	private void updateRoleMenu(String roleId,Map<String, Object> formMenuMap) throws BusinessException {
		//角色拥有的菜单
		List<String> myMenuList = roleMenuDao.findMenuIdByRoleId(roleId);
		Map<String, Object> myMenuMap = new HashMap<>();	//key:menuId   value:null
		for(String sm : myMenuList) {
			myMenuMap.put(sm, null);
		}
		List<String> sameKeyList = new ArrayList<>();
		for(Map.Entry<String, Object> formMenuEntry : formMenuMap.entrySet()) {
			for(Map.Entry<String, Object> myMenuEntry : myMenuMap.entrySet()) {
				if(StringUtils.equals(formMenuEntry.getKey(), myMenuEntry.getKey())) {
					sameKeyList.add(formMenuEntry.getKey());
				}
			}
		}
		for(String s : sameKeyList) {
			formMenuMap.remove(s);
			myMenuMap.remove(s);
		}
		for(Map.Entry<String, Object> myMenuEntry : myMenuMap.entrySet()) {
			int de = roleMenuDao.deleteByRoleIdAndMenuId(roleId, myMenuEntry.getKey());
			AssertUtil.assertOne(de, "删除角色菜单关联关系失败");
		}
		for(Map.Entry<String, Object> formMenuEntry : formMenuMap.entrySet()) {
			PubRoleMenuPO po = new PubRoleMenuPO();
			po.setRoleId(roleId);
			po.setMenuId(formMenuEntry.getKey());
			Integer in = roleMenuDao.insert(po);
			AssertUtil.assertOne(in, "插入角色菜单关联关系失败");
		}
	}
	
}
