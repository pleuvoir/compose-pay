package io.github.pleuvoir.manager.model.vo.pub;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pub.PubOperationLogPO;
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

/**
 * 操作日志列表
 * @author abeir
 *
 */
public class PubOperationLogListVO extends AbstractJqGridVO<PubOperationLogPO> {

	public PubOperationLogListVO(Pagination pagination) {
		super(pagination);
	}

}
