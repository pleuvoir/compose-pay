package io.github.pleuvoir.manager.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.PayWayFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayWayPO;

public interface PayWayDao extends BaseMapper<PayWayPO> {

	List<PayWayPO> find(PageCondition page, @Param("form") PayWayFormDTO form);

}
