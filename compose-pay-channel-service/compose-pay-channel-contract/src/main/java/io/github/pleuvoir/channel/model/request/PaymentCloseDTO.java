package io.github.pleuvoir.channel.model.request;


import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 关闭订单
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentCloseDTO extends AbstractReqModel {
}
