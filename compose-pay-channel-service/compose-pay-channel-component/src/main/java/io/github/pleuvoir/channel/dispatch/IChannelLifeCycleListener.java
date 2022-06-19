package io.github.pleuvoir.channel.dispatch;

import io.github.pleuvoir.channel.model.shared.AbstractReqModel;
import io.github.pleuvoir.channel.model.shared.AbstractRspModel;

/**
 * 通道请求生命周期监听器
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IChannelLifeCycleListener {

    void beforeRequest(AbstractReqModel reqModel);

    void afterRequest(AbstractReqModel reqModel, AbstractRspModel rspModel);

    void exceptionCaught(Throwable throwable);


    class Adapter implements IChannelLifeCycleListener {


        @Override
        public void beforeRequest(AbstractReqModel reqModel) {

        }

        @Override
        public void afterRequest(AbstractReqModel reqModel, AbstractRspModel rspModel) {

        }

        @Override
        public void exceptionCaught(Throwable throwable) {

        }
    }
}
