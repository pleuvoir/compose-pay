package io.github.pleuvoir.channel.model.shared;

import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractReqModel extends AbstractModel {

    private ChannelEnum channel;

    private ChannelMerDTO channelMerDTO;


    @NotBlank(message = "通道商户号不能为空")
    private String channelMid;

    @NotNull(message = "支付产品编码不能为空")
    private Integer payProduct;

}
