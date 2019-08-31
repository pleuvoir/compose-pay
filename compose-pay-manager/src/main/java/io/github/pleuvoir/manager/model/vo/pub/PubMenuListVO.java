package io.github.pleuvoir.manager.model.vo.pub;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pub.PubMenuPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PubMenuListVO extends AbstractJqGridVO<PubMenuPO> {

	public PubMenuListVO(Pagination pagination) {
		super(pagination);
	}

}
