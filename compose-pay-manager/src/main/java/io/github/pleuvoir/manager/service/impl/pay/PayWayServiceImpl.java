package io.github.pleuvoir.manager.service.impl.pay;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.dao.pay.PayWayDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.PayWayFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayWayPO;
import io.github.pleuvoir.manager.model.vo.pay.PayWayListVO;
import io.github.pleuvoir.manager.service.pay.PayWayService;

@Service
public class PayWayServiceImpl implements PayWayService {

	@Autowired
	private PayWayDao payWayDao;

	@Override
	public PayWayListVO queryList(PayWayFormDTO form) {

		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getPayWayName())){
			form.setPayWayName("%".concat(form.getPayWayName()).concat("%"));
		}
		
		List<PayWayPO> list = payWayDao.find(pageCondition, form);

		PayWayListVO result = new PayWayListVO(pageCondition);
		result.setRows(list);
		return result;

	}

	@Override
	public void save(PayWayPO po) throws BusinessException {
		
		PayWayPO entity = new PayWayPO();
		entity.setPayWayCode(po.getPayWayCode());
		PayWayPO prev = payWayDao.selectOne(entity);
		
		if (prev != null) {
			throw new BusinessException("已存在唯一记录");
		}
		Integer rs = payWayDao.insert(po);
		AssertUtil.assertOne(rs, "保存失败");
	}

	@Override
	public void modify(PayWayPO po) throws BusinessException {
		Integer rs = payWayDao.updateAllColumnById(po);
		AssertUtil.assertOne(rs, "修改失败");
	}

	@Override
	public void remove(String id) throws BusinessException {
		Integer rs = payWayDao.deleteById(id);
		AssertUtil.assertOne(rs, "删除失败");
	}

	@Override
	public PayWayPO selectById(String id) {
		return payWayDao.selectById(id);
	}

}
