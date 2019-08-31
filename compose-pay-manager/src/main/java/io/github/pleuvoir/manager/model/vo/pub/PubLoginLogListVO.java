package io.github.pleuvoir.manager.model.vo.pub;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pub.PubLoginLogPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PubLoginLogListVO extends AbstractJqGridVO<PubLoginLogPO> {

	public PubLoginLogListVO(Pagination pagination) {
		super(pagination);
	}

}
