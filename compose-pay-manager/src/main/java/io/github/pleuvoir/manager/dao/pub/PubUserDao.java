package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubUserFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 登录用户
 * @author LaoShu
 *
 */
public interface PubUserDao extends BaseMapper<PubUserPO> {
	
	/**
	 * 按条件分页查询
	 * @param page
	 * @param form
	 * @return
	 */
	List<PubUserPO> find(PageCondition page, @Param("form")PubUserFormDTO form);
	
	PubUserPO selectByUserName(@Param("userName")String userName);

}
