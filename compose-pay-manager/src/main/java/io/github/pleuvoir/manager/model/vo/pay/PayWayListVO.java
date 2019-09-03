package io.github.pleuvoir.manager.model.vo.pay;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pay.PayWayPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PayWayListVO extends AbstractJqGridVO<PayWayPO> {
	
	public PayWayListVO(Pagination pagination) {
		super(pagination);
	}

}
