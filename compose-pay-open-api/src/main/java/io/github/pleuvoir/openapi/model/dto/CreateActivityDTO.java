package io.github.pleuvoir.openapi.model.dto;

import io.github.pleuvoir.openapi.common.utils.ToJSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("创建红包活动返回实体类")
public class CreateActivityDTO implements ToJSON {

    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty("用户编号")
    private Long userId;
    @ApiModelProperty("红包总数")
    private Integer total;

}
