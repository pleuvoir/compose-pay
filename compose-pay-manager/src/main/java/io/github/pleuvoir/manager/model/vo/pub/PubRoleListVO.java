package io.github.pleuvoir.manager.model.vo.pub;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class PubRoleListVO extends AbstractJqGridVO<PubRolePO> {

	public PubRoleListVO(Pagination pagination) {
		super(pagination);
	}

}
