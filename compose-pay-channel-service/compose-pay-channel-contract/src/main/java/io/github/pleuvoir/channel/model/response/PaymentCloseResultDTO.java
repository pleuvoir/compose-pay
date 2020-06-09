package io.github.pleuvoir.channel.model.response;


import io.github.pleuvoir.channel.model.shared.AbstractRspModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 关闭订单结果
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentCloseResultDTO extends AbstractRspModel {
}
