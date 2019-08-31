package io.github.pleuvoir.manager.dao.pub;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubOperationLogFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubOperationLogPO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志DAO
 * @author abeir
 *
 */
public interface PubOperationLogDao extends BaseMapper<PubOperationLogPO>{
	/**
	 * 条件查询操作日志
	 * @param page
	 * @param form
	 * @return
	 */
	List<PubOperationLogPO> find(PageCondition page, @Param("log") PubOperationLogFormDTO form);
}
