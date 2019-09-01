package io.github.pleuvoir.manager.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.PayTypeFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayTypePO;

public interface PayTypeDao extends BaseMapper<PayTypePO> {

	List<PayTypePO> find(PageCondition page, @Param("form") PayTypeFormDTO form);

}
