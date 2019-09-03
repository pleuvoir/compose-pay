package io.github.pleuvoir.manager.model.dto.pay;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayProductFormDTO extends AbstractFormPageDTO {

	private String id;
	private String payTypeCode;
	private String payWayCode;
	private String name;
	private String status;
	private String remark;
}
