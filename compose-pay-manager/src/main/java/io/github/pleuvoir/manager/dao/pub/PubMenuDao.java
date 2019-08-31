package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubMenuFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubMenuPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单
 * @author LaoShu
 *
 */
public interface PubMenuDao extends BaseMapper<PubMenuPO> {
	
	/**
	 * 按条件分页查询
	 * @param page
	 * @param form
	 * @return
	 */
	List<PubMenuPO> find(PageCondition page, @Param("form")PubMenuFormDTO form);
	
	/**
	 * 用户所有的菜单
	 * @param username
	 * @return
	 */
	List<PubMenuPO> findMenuByUsername(@Param("username")String username);
	
	/**
	 * 用户所有的菜单(包含隐藏的菜单)
	 * @param username
	 * @return
	 */
	List<PubMenuPO> findAllMenuByUsername(@Param("username")String username);
	
	/**
	 * 用户所有的 node级菜单
	 * @param username
	 * @param node
	 * @return
	 */
	List<PubMenuPO> findMenuByUsernameAndNode(@Param("username")String username,@Param("node")String node);

	/**
	 * 获取下一个菜单id
	 * @return
	 */
	String getNextMenuId();
	
	/**
	 * 获取下一个排序值
	 * @param parentMenuId
	 * @return
	 */
	String getNextSort(@Param("parentId")String parentMenuId);
	
	/**
	 * 获取子菜单的数量
	 * @param parentMenuId
	 * @return
	 */
	int countByParentMenuId(@Param("parentId")String parentMenuId);
	
}
