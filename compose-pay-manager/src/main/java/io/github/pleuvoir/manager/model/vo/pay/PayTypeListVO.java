package io.github.pleuvoir.manager.model.vo.pay;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pay.PayTypePO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PayTypeListVO extends AbstractJqGridVO<PayTypePO> {
	
	public PayTypeListVO(Pagination pagination) {
		super(pagination);
	}

}
