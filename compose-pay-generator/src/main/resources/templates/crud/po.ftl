package io.github.pleuvoir.manager.model.po.pay;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.persistence.Column;
import lombok.Data;

@Data
@TableName("${dataModel.tableName?lower_case}")
public class ${dataModel.POName} {
	
	
	<#list dataModel.metaData.columnExtendList as columnExtend>
	@Column(name = "${columnExtend.columnName?lower_case}", length = ${columnExtend.columnDisplaySize}, nullable = ${columnExtend.isNullable})
	private ${columnExtend.convertedType} ${columnExtend.field};
	
	</#list>

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
	