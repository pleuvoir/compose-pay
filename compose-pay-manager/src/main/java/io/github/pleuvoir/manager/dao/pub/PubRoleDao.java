package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubRoleFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色
 * @author LaoShu
 *
 */
public interface PubRoleDao extends BaseMapper<PubRolePO> {
	
	/**
	 * 用户拥有的角色
	 * @param username
	 * @return id集合
	 */
	public List<PubRolePO> findRolesByUsername(@Param("username")String username);
	
	/**
	 * 用户创建的角色
	 * @param createBy
	 * @return
	 */
	public List<PubRolePO> findRliesByCreateBy(@Param("createBy")String createBy);
	
	/**
	 * 按条件分页查询
	 * @param page
	 * @param form
	 * @return
	 */
	List<PubRolePO> find(PageCondition page, @Param("form")PubRoleFormDTO form);

}
