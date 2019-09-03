package io.github.pleuvoir.manager.model.dto.pay;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayWayFormDTO extends AbstractFormPageDTO {

	private String id;
	private String payWayCode;
	private String payWayName;
	private String remark;
}
