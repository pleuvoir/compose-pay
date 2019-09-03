package io.github.pleuvoir.manager.service.pay;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pay.${dataModel.name}FormDTO;
import io.github.pleuvoir.manager.model.po.pay.${dataModel.name}PO;
import io.github.pleuvoir.manager.model.vo.pay.${dataModel.name}ListVO;

public interface ${dataModel.name}Service {

	/**
	 * 列表查询
	 */
	${dataModel.name}ListVO queryList(${dataModel.name}FormDTO form);

	/**
	 * 保存
	 */
	void save(${dataModel.POName} po) throws BusinessException;

	/**
	 * 修改
	 */
	void modify(${dataModel.POName} po) throws BusinessException;

	/**
	 * 删除
	 */
	void remove(String id) throws BusinessException;
	
	/**
	 * 根据id查找
	 */
	${dataModel.POName} selectById(String id);
}
