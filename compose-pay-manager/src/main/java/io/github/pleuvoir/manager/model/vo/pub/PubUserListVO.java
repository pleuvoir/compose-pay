package io.github.pleuvoir.manager.model.vo.pub;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pub.PubUserPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PubUserListVO extends AbstractJqGridVO<PubUserPO> {

	public PubUserListVO(Pagination pagination) {
		super(pagination);
	}

}
