package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.po.pub.PubRoleMenuPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联关系
 * @author LaoShu
 *
 */
public interface PubRoleMenuDao extends BaseMapper<PubRoleMenuPO> {
	
	/**
	 * 删除角色菜单关联关系
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(@Param("roleId")String roleId);
	
	/**
	 * 删除角色菜单关联关系
	 * @param menuId
	 * @return
	 */
	int deleteByMenuId(@Param("menuId")String menuId);
	
	/**
	 * 删除角色菜单关联关系
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	int deleteByRoleIdAndMenuId(@Param("roleId")String roleId,@Param("menuId")String menuId);
	
	/**
	 * 获取角色拥有的菜单id
	 * @param roleId
	 * @return
	 */
	List<String> findMenuIdByRoleId(@Param("roleId")String roleId);

}
