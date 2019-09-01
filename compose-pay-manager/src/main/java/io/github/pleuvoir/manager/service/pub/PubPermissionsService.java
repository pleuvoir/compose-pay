package io.github.pleuvoir.manager.service.pub;

import java.util.List;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubMenuFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;

/**
 * 权限
 *
 */
public interface PubPermissionsService {
	
	/**
	 * 用户拥有的权限
	 * @param username
	 * @return code集合
	 */
	List<PubPermissionPO> findPermissionsByUsername(String username);
	/**
	 * 根据权限code查询
	 * @param code
	 * @return
	 */
	PubPermissionPO getPermission(String code);
	
	/**
	 * 根据权限id查询
	 * @param id
	 * @return
	 */
	PubPermissionPO getPermissionById(String id);
	
	/**
	 * 菜单拥有的权限
	 * @param menuId
	 * @return
	 */
	List<PubPermissionPO> findPermissionsByMenuId(String menuId);
	
	/**
	 * 保存
	 * @param dto
	 * @throws BusinessException
	 */
	void save(PubMenuFormDTO dto) throws BusinessException;
}
