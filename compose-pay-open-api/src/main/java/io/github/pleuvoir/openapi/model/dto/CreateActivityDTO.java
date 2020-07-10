package io.github.pleuvoir.redpack.model.dto;

import io.github.pleuvoir.redpack.common.utils.ToJSON;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
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
