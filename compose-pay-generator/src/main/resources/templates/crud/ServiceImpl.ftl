package io.github.pleuvoir.manager.service.impl.pay;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.dao.pay.${dataModel.name}Dao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.${dataModel.name}FormDTO;
import io.github.pleuvoir.manager.model.po.pay.${dataModel.POName};
import io.github.pleuvoir.manager.model.vo.pay.${dataModel.name}ListVO;
import io.github.pleuvoir.manager.service.pay.${dataModel.name}Service;

@Service
public class ${dataModel.name}ServiceImpl implements ${dataModel.name}Service {

	@Autowired
	private ${dataModel.name}Dao ${dataModel.name?uncap_first}Dao;

	@Override
	public ${dataModel.name}ListVO queryList(${dataModel.name}FormDTO form) {

		PageCondition pageCondition = PageCondition.create(form);
		
	//	if(StringUtils.isNotBlank(form.getPayTypeName())){
	//		form.setPayTypeName("%".concat(form.getPayTypeName()).concat("%"));
	//	}
		
		List<${dataModel.POName}> list = ${dataModel.name?uncap_first}Dao.find(pageCondition, form);

		${dataModel.name}ListVO result = new ${dataModel.name}ListVO(pageCondition);
		result.setRows(list);
		return result;

	}

	@Override
	public void save(${dataModel.POName} po) throws BusinessException {
		
	//	${dataModel.POName} entity = new ${dataModel.POName}();
	//	entity.setPayTypeCode(po.getPayTypeCode());
	//	${dataModel.POName} prev = ${dataModel.name?uncap_first}Dao.selectOne(entity);
		
	//	if (prev != null) {
	//		throw new BusinessException("已存在唯一记录");
	//	}
		Integer rs = ${dataModel.name?uncap_first}Dao.insert(po);
		AssertUtil.assertOne(rs, "保存失败");
	}

	@Override
	public void modify(${dataModel.POName} po) throws BusinessException {
		Integer rs = ${dataModel.name?uncap_first}Dao.updateAllColumnById(po);
		AssertUtil.assertOne(rs, "修改失败");
	}

	@Override
	public void remove(String id) throws BusinessException {
		Integer rs = ${dataModel.name?uncap_first}Dao.deleteById(id);
		AssertUtil.assertOne(rs, "删除失败");
	}

	@Override
	public ${dataModel.POName} selectById(String id) {
		return ${dataModel.name?uncap_first}Dao.selectById(id);
	}

}
