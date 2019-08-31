package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.po.pub.PubRolePermissionPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联关系
 * @author LaoShu
 *
 */
public interface PubRolePermissionDao extends BaseMapper<PubRolePermissionPO> {
	
	/**
	 * 删除角色权限关联关系
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(@Param("roleId")String roleId);
	
	/**
	 * 删除角色权限关联关系
	 * @param permissionId
	 * @return
	 */
	int deleteByPermissionId(@Param("permissionId")String permissionId);
	
	/**
	 * 删除角色权限关联关系
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	int deleteByRoleIdAndPermissionId(@Param("roleId")String roleId,@Param("permissionId")String permissionId);
	
	/**
	 * 获取角色拥有的权限id
	 * @param roleId
	 * @return
	 */
	List<String> findPermissionIdByRoleId(@Param("roleId")String roleId);

}
