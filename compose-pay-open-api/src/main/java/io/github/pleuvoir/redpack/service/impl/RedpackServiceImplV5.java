package io.github.pleuvoir.redpack.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.common.collect.Lists;

import io.github.pleuvoir.redpack.common.Const;
import io.github.pleuvoir.redpack.common.RspCode;
import io.github.pleuvoir.redpack.common.utils.IdUtils;
import io.github.pleuvoir.redpack.dao.RedpackActivityDao;
import io.github.pleuvoir.redpack.dao.RedpackDao;
import io.github.pleuvoir.redpack.dao.RedpackDetailDao;
import io.github.pleuvoir.redpack.exception.RedpackException;
import io.github.pleuvoir.redpack.model.dto.CreateActivityDTO;
import io.github.pleuvoir.redpack.model.dto.CreateActivityResultDTO;
import io.github.pleuvoir.redpack.model.dto.FightRedpackDTO;
import io.github.pleuvoir.redpack.model.po.RedpackActivityPO;
import io.github.pleuvoir.redpack.model.po.RedpackDetailPO;
import io.github.pleuvoir.redpack.model.po.RedpackPO;
import io.github.pleuvoir.redpack.service.IRedpackService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>将冲突分散到红包表，在创建活动的时候预先分配红包，并加入redis队列，然后直接更新</p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service("redpackServiceImplV5")
public class RedpackServiceImplV5 implements IRedpackService {

    @Autowired
    private RedpackActivityDao activityDao;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager txManager;
    @Autowired
    private RedisTemplate<String, RedpackPO> redisTemplate;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public CreateActivityResultDTO create(CreateActivityDTO dto) {
        RedpackActivityPO redpackActivityPO = new RedpackActivityPO();
        redpackActivityPO.setVersion(0);
        redpackActivityPO.setSurplusAmount(BigDecimal.ZERO);
        redpackActivityPO.setTotalAmount(dto.getTotalAmount());
        redpackActivityPO.setTotal(dto.getTotal());
        redpackActivityPO.setSurplusTotal(0);
        Long id = IdUtils.nextId();
        redpackActivityPO.setId(id);
        activityDao.insert(redpackActivityPO);

        //平均分配
        BigDecimal decimal = dto.getTotalAmount().divide(new BigDecimal(dto.getTotal()), RoundingMode.HALF_DOWN)
                .setScale(2, RoundingMode.HALF_DOWN);

        List<RedpackPO> queue = Lists.newArrayList();
        for (Integer i = 0; i < dto.getTotal(); i++) {
            RedpackPO redpackPO = new RedpackPO();
            redpackPO.setId(IdUtils.nextId());
            redpackPO.setAmount(decimal);
            redpackPO.setStatus(RedpackPO.ENABLE);
            redpackPO.setActivityId(redpackActivityPO.getId());
            redpackPO.setVersion(0);
            redpackDao.insert(redpackPO);
            queue.add(redpackPO);
        }

        //加入redis
        String queueKey = Const.REDIS_QUEUE_NAME.concat(redpackActivityPO.getId().toString());
        redisTemplate.opsForList().rightPushAll(queueKey, queue);

        // https://github.com/alibaba/fastjson/issues/2780 1.2.62有bug
        return new CreateActivityResultDTO(id);
    }

    @Override
    public boolean fight(FightRedpackDTO dto) throws RedpackException {
        Long activityId = dto.getActivityId();

        String queueKey = Const.REDIS_QUEUE_NAME.concat(activityId.toString());

        RedpackPO po = this.redisTemplate.opsForList().leftPop(queueKey);
        if (po == null) {
            return false;
        }

        // 开启事物
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            po.setStatus(RedpackPO.DISABLE);
            redpackDao.updateById(po);
            RedpackDetailPO detailPO = new RedpackDetailPO();
            detailPO.setAmount(po.getAmount());
            detailPO.setRedpackId(po.getActivityId());
            detailPO.setUserId(dto.getUserId());
            detailDao.insert(detailPO);
            txManager.commit(txStatus);
        } catch (Throwable e) {
            log.error("", e);
            txManager.rollback(txStatus);
            throw new RedpackException(RspCode.ERROR);
        }

        return true;
    }

}
