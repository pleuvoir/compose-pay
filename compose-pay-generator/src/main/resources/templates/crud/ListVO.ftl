package io.github.pleuvoir.manager.model.vo.pay;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.po.pay.${dataModel.POName};
import io.github.pleuvoir.manager.model.vo.AbstractJqGridVO;

public class ${dataModel.name}ListVO extends AbstractJqGridVO<${dataModel.POName}> {
	
	public ${dataModel.name}ListVO(Pagination pagination) {
		super(pagination);
	}

}
