package io.github.pleuvoir.redpack.thread;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.redpack.common.AppConfig;
import io.github.pleuvoir.redpack.common.Const;
import io.github.pleuvoir.redpack.dao.RedpackDao;
import io.github.pleuvoir.redpack.dao.RedpackDetailDao;
import io.github.pleuvoir.redpack.model.dto.RedpackPersistDTO;
import io.github.pleuvoir.redpack.model.po.RedpackDetailPO;
import io.github.pleuvoir.redpack.model.po.RedpackPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 用户抢到红包后进行异步持久化操作
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Component
public class UserRedpackPersistConsumer {

    @Qualifier("threadPoolTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisTemplate<String, RedpackPersistDTO> persistRedisTemplate;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager txManager;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;

    @PostConstruct
    public void init() throws InterruptedException {
        if (!appConfig.isPersist()) {
            log.warn("未开启红包异步持久化线程。");
            return;
        }

        log.info("开启红包异步持久化线程。");

        taskExecutor.execute(this::persist);

    }


    protected void persist() {

        ListOperations<String, RedpackPersistDTO> listOps = this.persistRedisTemplate.opsForList();

        while (!Thread.currentThread().isInterrupted()) {

            // BRPOPLPUSH
            RedpackPersistDTO persistDTO = listOps.rightPopAndLeftPush(Const.REDIS_PERSIST_QUEUE_NAME,
                    Const.REDIS_PERSIST_QUEUE_NAME_BAK, 3, TimeUnit.SECONDS);

            if (persistDTO == null) {
                continue;
            }

            log.info("获取到待入库红包，{}", JSON.toJSONString(persistDTO));

            RedpackPO redpackPO = new RedpackPO();
            BeanUtils.copyProperties(persistDTO, redpackPO);

            // 开启事物
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = txManager.getTransaction(def);
            try {
                redpackPO.setStatus(RedpackPO.DISABLE);
                redpackDao.updateById(redpackPO);
                RedpackDetailPO detailPO = new RedpackDetailPO();
                detailPO.setId(persistDTO.getId());
                detailPO.setAmount(redpackPO.getAmount());
                detailPO.setRedpackId(redpackPO.getActivityId());
                detailPO.setUserId(persistDTO.getUserId());
                detailPO.setCreateTime(persistDTO.getCreateTime());
                detailDao.insert(detailPO);
                txManager.commit(txStatus);
                log.info("待入库红包已入库，{}", JSON.toJSONString(persistDTO));
            } catch (Throwable e) {
                //记录失败日志，方便排查原因
                AsyncPersistLogger.save("FAIL", "saveRedisRedpackPersistDTO", JSON.toJSONString(persistDTO));
                txManager.rollback(txStatus);
                log.error("异步持久化错误，", e);
            }
        }
    }
}
