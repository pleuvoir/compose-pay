package io.github.pleuvoir.channel.model.shared;

import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.TransEnum;
import io.github.pleuvoir.channel.model.ChannelMerDTO;
import lombok.Data;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
public class AbstractReqModel extends AbstractModel {

    private ChannelEnum channel;

    private TransEnum trans;

    private ChannelMerDTO channelMerDTO;

}
