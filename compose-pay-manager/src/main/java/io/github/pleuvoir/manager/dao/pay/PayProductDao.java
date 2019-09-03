package io.github.pleuvoir.manager.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.PayProductFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayProductPO;

public interface PayProductDao extends BaseMapper<PayProductPO> {

	List<PayProductPO> find(PageCondition page, @Param("form") PayProductFormDTO form);

}
