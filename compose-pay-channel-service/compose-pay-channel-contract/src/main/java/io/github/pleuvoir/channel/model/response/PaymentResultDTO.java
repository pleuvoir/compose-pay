package io.github.pleuvoir.channel.model.response;


import io.github.pleuvoir.channel.model.shared.AbstractRspModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 下单结果
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentResultDTO extends AbstractRspModel {

    /**
     * 发起支付需要的参数信息字符串
     */
    private String paramStr;
}
