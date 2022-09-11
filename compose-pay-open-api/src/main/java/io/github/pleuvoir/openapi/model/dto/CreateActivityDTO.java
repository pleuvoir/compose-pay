package io.github.pleuvoir.openapi.model.dto;

import io.github.pleuvoir.openapi.common.utils.ToJSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateActivityDTO implements ToJSON {

    private BigDecimal totalAmount;

    private Long userId;

    private Integer total;

}
