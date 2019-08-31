package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubParamFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubParamPO;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 参数DAO
 * @author abeir
 *
 */
public interface PubParamDao extends BaseMapper<PubParamPO>{
	/**
	 * 按条件分页查询
	 * @param page
	 * @param form
	 * @return
	 */
	List<PubParamPO> find(PageCondition page, @Param("form")PubParamFormDTO form);
	
	String getStringValue(@Param("code")String code);
	
	BigDecimal getDecimalValue(@Param("code")String code);
	
	Integer getIntegerValue(@Param("code")String code);
	
	Boolean getBooleanValue(@Param("code")String code);
	/**
	 * 判断code是否存在
	 * @param code
	 * @return
	 */
	int countByCode(@Param("code")String code);
}
