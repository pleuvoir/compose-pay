package io.github.pleuvoir.manager.model.po.pay;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("${dataModel.tableName?lower_case}")
public class ${dataModel.entityName} {
	
	
	<#list dataModel.metaData.columnExtendList as columnExtend>
	private ${columnExtend.convertedType} ${columnExtend.field};
	
	</#list>

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
	