package io.github.pleuvoir.redpack.thread;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.AbstractScheduledService;
import io.github.pleuvoir.redpack.common.AppConfig;
import io.github.pleuvoir.redpack.common.Const;
import io.github.pleuvoir.redpack.dao.RedpackDao;
import io.github.pleuvoir.redpack.dao.RedpackDetailDao;
import io.github.pleuvoir.redpack.model.dto.RedpackPersistDTO;
import io.github.pleuvoir.redpack.model.po.RedpackDetailPO;
import io.github.pleuvoir.redpack.model.po.RedpackPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 当{@link UserRedpackPersistConsumer#persist}  拉取到入库消息，但是入库失败时，需要从备份队列进行检查<br>
 * 如果之前没有入库成功则进行补偿，补偿完成后删除备份队列数据<br>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Component
public class CrashRecoveryThread extends AbstractScheduledService implements InitializingBean, DisposableBean {

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager txManager;
    @Autowired
    private RedisTemplate<String, RedpackPersistDTO> persistRedisTemplate;


    @Override
    protected void runOneIteration() {

        log.info("CrashRecoveryThread runOneIteration... ");

        List<RedpackPersistDTO> persistDTOS = persistRedisTemplate.opsForList().range(Const.REDIS_PERSIST_QUEUE_NAME_BAK, 0, -1);
        if (CollectionUtils.isEmpty(persistDTOS)) {
            log.info("CrashRecoveryThread found no bak records, ignore. ");
            return;
        }

        log.info("CrashRecoveryThread get {} size record to testRecovery... ", persistDTOS.size());

        for (RedpackPersistDTO persistDTO : persistDTOS) {
            this.testRecovery(persistDTO);
        }
    }


    private void testRecovery(RedpackPersistDTO persistDTO) {
        Long id = persistDTO.getId();
        RedpackDetailPO redpackDetailPO = detailDao.selectById(id);
        if (redpackDetailPO == null) {
            log.info("CrashRecoveryThread found id={} redpackDetail record not exist , try to recovery it...", id);
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
                detailPO.setAmount(redpackPO.getAmount());
                detailPO.setRedpackId(redpackPO.getActivityId());
                detailPO.setUserId(persistDTO.getUserId());
                detailPO.setCreateTime(persistDTO.getCreateTime());
                detailDao.insert(detailPO);
                txManager.commit(txStatus);
                // Lrem
                persistRedisTemplate.opsForList().remove(Const.REDIS_PERSIST_QUEUE_NAME_BAK, 0, persistDTO);
            } catch (Throwable e) {
                //记录失败日志，方便排查原因
                AsyncPersistLogger.save("FAIL", "recovery", JSON.toJSONString(persistDTO));
                txManager.rollback(txStatus);
                log.error("recovery error，", e);
            }
        } else {
            log.info("redpackDetailPO[{}] exist, try Lrem it .", persistDTO.getId());
            // Lrem
            persistRedisTemplate.opsForList().remove(Const.REDIS_PERSIST_QUEUE_NAME_BAK, 0, persistDTO);
        }
    }


    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(5, appConfig.getCrashRecoveryInteval(), TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.startAsync();
        log.info("CrashRecoveryThread startUp.");
    }

    @Override
    public void destroy() throws Exception {
        super.shutDown();
        log.info("CrashRecoveryThread shutDown.");
    }
}
