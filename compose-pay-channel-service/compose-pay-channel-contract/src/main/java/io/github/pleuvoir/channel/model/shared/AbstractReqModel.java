package io.github.pleuvoir.channel.model.shared;

import io.github.pleuvoir.channel.model.ChannelMerDTO;
import io.github.pleuvoir.pay.common.enums.ChannelEnum;
import io.github.pleuvoir.pay.common.enums.ServiceIdEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractReqModel extends AbstractModel {

    private ChannelEnum channel;

    private ChannelMerDTO channelMerDTO;

    private ServiceIdEnum serviceId;

}
