package io.github.pleuvoir.channel.model.request;


import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 下单
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentDTO extends AbstractReqModel {


    @NotBlank(message = "支付订单号不能为空")
    private String orderId;

    @NotBlank(message = "商品名称不能为空")
    private String subject;

    @NotBlank(message = "商品描述不能为空")
    private String body;

    /**
     * 附加参数，支付查询和通知时会原样返回
     */
    private String attach;

    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    /**
     * 扩展参数（不同通道支付产品差异化传参）
     */
    private Map<String, String> ext;


}
