package io.github.pleuvoir.job.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 支付结果查证
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Component
public class PayVerifyJob {

    @XxlJob("payVerifyHandler")
    public ReturnT<String> payVerifyHandler(String param) throws Exception {

        log.info(" -=-=-=-=-=-= 开始支付结果查证 -=-=-=-=-=-= ");

        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(2);
        }

        log.info(" -=-=-=-=-=-= 结束支付结果查证 -=-=-=-=-=-=");
        return ReturnT.SUCCESS;
    }
}
