package io.github.pleuvoir.manager.model.vo.pub;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pub.PubParamPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PubParamListVO extends AbstractJqGridVO<PubParamPO> {

	public PubParamListVO(Pagination pagination) {
		super(pagination);
	}

}
