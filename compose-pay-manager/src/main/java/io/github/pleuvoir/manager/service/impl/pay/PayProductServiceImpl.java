package io.github.pleuvoir.manager.service.impl.pay;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.dao.pay.PayProductDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.PayProductFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayProductPO;
import io.github.pleuvoir.manager.model.vo.pay.PayProductListVO;
import io.github.pleuvoir.manager.service.pay.PayProductService;

@Service
public class PayProductServiceImpl implements PayProductService {

	@Autowired
	private PayProductDao payProductDao;

	@Override
	public PayProductListVO queryList(PayProductFormDTO form) {

		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getName())){
			form.setName("%".concat(form.getName()).concat("%"));
		}
		
		List<PayProductPO> list = payProductDao.find(pageCondition, form);

		PayProductListVO result = new PayProductListVO(pageCondition);
		result.setRows(list);
		return result;

	}

	@Override
	public void save(PayProductPO po) throws BusinessException {
		
		PayProductPO entity = new PayProductPO();
		entity.setPayTypeCode(po.getPayTypeCode());
		entity.setPayWayCode(po.getPayWayCode());
		PayProductPO prev = payProductDao.selectOne(entity);
		
		if (prev != null) {
			throw new BusinessException("已存在唯一记录");
		}
		
		po.setStatus(PayProductPO.STATUS_ENABLE);
		Integer rs = payProductDao.insert(po);
		AssertUtil.assertOne(rs, "保存失败");
	}

	@Override
	public void modify(PayProductPO po) throws BusinessException {
		
		PayProductPO prev = payProductDao.selectById(po.getId());
		if (prev == null) {
			throw new BusinessException("未找到记录");
		}
		prev.setName(StringUtils.trim(po.getName()));
		prev.setRemark(StringUtils.trim(po.getRemark()));
		prev.setStatus(po.getStatus());
		
		
		Integer rs = payProductDao.updateAllColumnById(prev);
		AssertUtil.assertOne(rs, "修改失败");
	}

	@Override
	public void remove(String id) throws BusinessException {
		Integer rs = payProductDao.deleteById(id);
		AssertUtil.assertOne(rs, "删除失败");
	}

	@Override
	public PayProductPO selectById(String id) {
		return payProductDao.selectById(id);
	}

}
