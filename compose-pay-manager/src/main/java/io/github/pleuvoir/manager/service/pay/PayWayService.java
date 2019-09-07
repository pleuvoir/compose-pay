package io.github.pleuvoir.manager.service.pay;

import java.util.List;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pay.PayWayFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayWayPO;
import io.github.pleuvoir.manager.model.vo.pay.PayWayListVO;

public interface PayWayService {

	/**
	 * 列表查询
	 */
	PayWayListVO queryList(PayWayFormDTO form);

	/**
	 * 保存
	 */
	void save(PayWayPO po) throws BusinessException;

	/**
	 * 修改
	 */
	void modify(PayWayPO po) throws BusinessException;

	/**
	 * 删除
	 */
	void remove(String id) throws BusinessException;
	
	/**
	 * 根据id查找
	 */
	PayWayPO selectById(String id);
	
	List<PayWayPO> all();
}
