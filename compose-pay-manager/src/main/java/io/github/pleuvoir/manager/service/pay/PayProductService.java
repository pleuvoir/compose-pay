package io.github.pleuvoir.manager.service.pay;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pay.PayProductFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayProductPO;
import io.github.pleuvoir.manager.model.vo.pay.PayProductListVO;

public interface PayProductService {

	/**
	 * 列表查询
	 */
	PayProductListVO queryList(PayProductFormDTO form);

	/**
	 * 保存
	 */
	void save(PayProductPO po) throws BusinessException;

	/**
	 * 修改
	 */
	void modify(PayProductPO po) throws BusinessException;

	/**
	 * 删除
	 */
	void remove(String id) throws BusinessException;
	
	/**
	 * 根据id查找
	 */
	PayProductPO selectById(String id);
}
