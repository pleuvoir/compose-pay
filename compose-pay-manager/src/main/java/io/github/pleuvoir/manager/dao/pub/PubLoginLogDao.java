package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubLoginLogFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubLoginLogPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PubLoginLogDao extends BaseMapper<PubLoginLogPO> {

	/**
	 * 按条件分页查询
	 * @param page
	 * @param form
	 * @return
	 */
	List<PubLoginLogPO> find(PageCondition page, @Param("form")PubLoginLogFormDTO form);
	
}
