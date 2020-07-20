package io.github.pleuvoir.openapi.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@AllArgsConstructor
@ApiModel("创建红包活动返回结果实体类")
public class CreateActivityResultDTO {

    @ApiModelProperty("活动id")
    private Long activityId;
}
