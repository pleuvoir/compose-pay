package io.github.pleuvoir.manager.service.pub;

import java.util.List;
import java.util.Map;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubPermissionFormDTO;
import io.github.pleuvoir.manager.model.dto.pub.PubRoleFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.vo.pub.PubRoleListVO;

/**
 * 角色
 * @author LaoShu
 *
 */
public interface PubRoleService {
	
	/**
	 * 用户拥有的角色
	 * @param username
	 * @return id集合
	 */
	List<PubRolePO> findRolesByUsername(String username);
	
	/**
	 * 用户创建的角色
	 * @param createBy
	 * @return
	 */
	public List<PubRolePO> findRliesByCreateBy(String createBy);
	
	/**
	 * 列表查询
	 * @param form
	 * @return
	 */
	PubRoleListVO queryList(PubRoleFormDTO form);
	
	/**
	 * 保存角色
	 * @param po
	 * @throws BusinessException
	 */
	public void save(PubRolePO po) throws BusinessException;
	
	/**
	 * 修改角色
	 * @param po
	 * @throws BusinessException
	 */
	public void edit(PubRolePO po) throws BusinessException;
	
	/**
	 * 删除角色
	 * @param id
	 * @throws BusinessException
	 */
	public void delete(String id) throws BusinessException;
	
	/**
	 * 通过id获取角色
	 * @param id
	 * @return
	 */
	public PubRolePO getById(String id);
	
	/**
	 * 生成树
	 * @param id 角色id
	 * @return
	 */
	public List<Map<String,Object>> getPermissionTree(String id);
	
	/**
	 * 权限分配保存
	 * @param dto
	 */
	public void permissionSave(PubPermissionFormDTO dto) throws BusinessException;

}
