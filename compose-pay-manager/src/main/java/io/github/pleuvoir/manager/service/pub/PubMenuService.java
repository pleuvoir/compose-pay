package io.github.pleuvoir.manager.service.pub;

import java.util.List;

import io.github.pleuvoir.manager.common.enums.MenuUser;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubMenuFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubMenuPO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuListVO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuView;

/**
 * 登录菜单
 * @author LaoShu
 *
 */
public interface PubMenuService {
	
	/**
	 * 列表查询
	 * @param dto
	 * @return
	 */
	PubMenuListVO queryList(PubMenuFormDTO dto);
	
	/**
	 * 通过id获取菜单
	 * @param id
	 * @return
	 */
	PubMenuPO getById(String id);
	
	/**
	 * 保存
	 * @param po
	 * @throws BusinessException
	 */
	void save(PubMenuPO po) throws BusinessException;
	
	/**
	 * 修改
	 * @param po
	 * @throws BusinessException
	 */
	void update(PubMenuPO po) throws BusinessException;
	
	/**
	 * 删除
	 * @param menuId
	 * @throws BusinessException
	 */
	void delete(String menuId) throws BusinessException;
	
	/**
	 * 排序
	 * @param jsonStr
	 * @throws BusinessException
	 */
	void sort(String jsonStr) throws BusinessException;
	
	/**
	 * 通过用户名获取菜单模型
	 * @param username
	 * @param menuUser
	 * @return
	 */
	List<PubMenuView> menuViewList(String username, MenuUser menuUser);
	
	/**
	 * 用户拥有的0级菜单
	 * @param username
	 * @return
	 */
	List<PubMenuPO> findMenuNodeZeroByUsername(String username);
	
	/**
	 * 用户拥有的一级菜单
	 * @param username
	 * @return
	 */
	List<PubMenuPO> findMenuNodeOneByUsername(String username);
	
	/**
	 * 用户拥有的二级菜单
	 * @param username
	 * @return
	 */
	List<PubMenuPO> findMenuNodeTwoByUsername(String username);
	
	/**
	 * 用户拥有的三级菜单
	 * @param username
	 * @return
	 */
	List<PubMenuPO> findMenuNodeThreeByUsername(String username);
	
	/**
	 * 菜单是否存在子菜单
	 * @param menuId
	 * @return
	 */
	Boolean menuHasChild(String menuId);
	
}
