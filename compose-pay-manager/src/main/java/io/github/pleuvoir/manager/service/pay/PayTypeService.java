package io.github.pleuvoir.manager.service.pay;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pay.PayTypeFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayTypePO;
import io.github.pleuvoir.manager.model.vo.pay.PayTypeListVO;

public interface PayTypeService {

	/**
	 * 列表查询
	 */
	PayTypeListVO queryList(PayTypeFormDTO form);

	/**
	 * 保存
	 */
	void save(PayTypePO po) throws BusinessException;

	/**
	 * 修改
	 */
	void modify(PayTypePO po) throws BusinessException;

	/**
	 * 删除
	 */
	void remove(String id) throws BusinessException;
	
	/**
	 * 根据id查找
	 */
	PayTypePO selectById(String id);
}
