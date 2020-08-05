package io.github.pleuvoir.channel.agent;

import com.alibaba.dubbo.config.annotation.Service;
import io.github.pleuvoir.channel.model.request.NotifyParamDTO;
import io.github.pleuvoir.channel.model.response.NotifyParamResultDTO;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 通道通知服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Component
@Service
public class NotifyChannelServiceAgentImpl implements INotifyChannelServiceAgent {

  @Override
  public NotifyParamResultDTO payNotifyReceive(@Valid NotifyParamDTO notifyParamDTO) {
    return null;
  }

}
