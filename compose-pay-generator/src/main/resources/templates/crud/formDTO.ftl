package io.github.pleuvoir.manager.model.dto.pay;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ${dataModel.name}FormDTO extends AbstractFormPageDTO {

	<#list dataModel.metaData.columnExtendList as columnExtend>
	private ${columnExtend.convertedType} ${columnExtend.field};
	</#list>
}
