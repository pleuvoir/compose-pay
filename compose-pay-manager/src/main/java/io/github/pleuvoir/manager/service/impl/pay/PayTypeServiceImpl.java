package io.github.pleuvoir.manager.service.impl.pay;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.dao.pay.PayTypeDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.PayTypeFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayTypePO;
import io.github.pleuvoir.manager.model.vo.pay.PayTypeListVO;
import io.github.pleuvoir.manager.service.pay.PayTypeService;

@Service
public class PayTypeServiceImpl implements PayTypeService {

	@Autowired
	private PayTypeDao payTypeDao;

	@Override
	public PayTypeListVO queryList(PayTypeFormDTO form) {

		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getPayTypeName())){
			form.setPayTypeName("%".concat(form.getPayTypeName()).concat("%"));
		}
		
		List<PayTypePO> list = payTypeDao.find(pageCondition, form);

		PayTypeListVO result = new PayTypeListVO(pageCondition);
		result.setRows(list);
		return result;

	}

	@Override
	public void save(PayTypePO po) throws BusinessException {
		
		PayTypePO entity = new PayTypePO();
		entity.setPayTypeCode(po.getPayTypeCode());
		PayTypePO prev = payTypeDao.selectOne(entity);
		
		if (prev != null) {
			throw new BusinessException("已存在唯一记录");
		}
		Integer rs = payTypeDao.insert(po);
		AssertUtil.assertOne(rs, "保存失败");
	}

	@Override
	public void modify(PayTypePO po) throws BusinessException {
		Integer rs = payTypeDao.updateAllColumnById(po);
		AssertUtil.assertOne(rs, "修改失败");
	}

	@Override
	public void remove(String id) throws BusinessException {
		Integer rs = payTypeDao.deleteById(id);
		AssertUtil.assertOne(rs, "删除失败");
	}

	@Override
	public PayTypePO selectById(String id) {
		return payTypeDao.selectById(id);
	}

}
