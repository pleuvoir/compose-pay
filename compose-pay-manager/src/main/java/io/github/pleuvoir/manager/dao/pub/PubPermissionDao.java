package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限
 * @author LaoShu
 *
 */
public interface PubPermissionDao extends BaseMapper<PubPermissionPO> {
	
	/**
	 * 用户拥有的权限
	 * @param username
	 * @return code集合
	 */
	List<PubPermissionPO> findPermissionsByUsername(@Param("username")String username);
	/**
	 * 根据code查询
	 * @param code
	 * @return
	 */
	PubPermissionPO getByCode(@Param("code") String code);
	
	/**
	 * 菜单拥有的权限
	 * @param menuId
	 * @return
	 */
	List<PubPermissionPO> findPermissionsByMenuId(@Param("menuId")String menuId);
	
	/**
	 * 获取最大id
	 * @param menuId
	 * @return
	 */
	String maxIdByMenuId(@Param("menuId")String menuId);

	/**
	 * 下一个权限id
	 */
	String nextPermissionId();

}
