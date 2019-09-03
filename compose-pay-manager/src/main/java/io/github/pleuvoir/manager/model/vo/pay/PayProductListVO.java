package io.github.pleuvoir.manager.model.vo.pay;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pay.PayProductPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PayProductListVO extends AbstractJqGridVO<PayProductPO> {
	
	public PayProductListVO(Pagination pagination) {
		super(pagination);
	}

}
