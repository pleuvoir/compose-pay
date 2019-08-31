package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.po.pub.PubUserRolePO;

import org.apache.ibatis.annotations.Param;

public interface PubUserRoleDao extends BaseMapper<PubUserRolePO> {
	
	/**
	 * 获取用户角色关联关系
	 * @param userId
	 * @param roleId
	 * @return
	 */
	PubUserRolePO getUserRole(@Param("userId")String userId,@Param("roleId")String roleId);
	
	/**
	 * 删除用户角色关联关系
	 * @param userId
	 * @param roleId
	 * @return
	 */
	int deleteByUserIdAndRoleId(@Param("userId")String userId,@Param("roleId")String roleId);
	
	/**
	 * 删除用户角色关联关系
	 * @param userId
	 * @return
	 */
	int deleteByUserId(@Param("userId")String userId);
	
	/**
	 * 查询是否存在关联关系
	 * @param roleId
	 * @return
	 */
	int countByRoleId(@Param("roleId")String roleId);

}
