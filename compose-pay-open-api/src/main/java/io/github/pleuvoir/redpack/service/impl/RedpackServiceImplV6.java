package io.github.pleuvoir.redpack.service.impl;

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
import io.github.pleuvoir.redpack.model.dto.RedpackPersistDTO;
import io.github.pleuvoir.redpack.model.po.RedpackActivityPO;
import io.github.pleuvoir.redpack.model.po.RedpackDetailPO;
import io.github.pleuvoir.redpack.model.po.RedpackPO;
import io.github.pleuvoir.redpack.service.IRedpackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>将冲突分散到红包表，在创建活动的时候预先分配红包，并加入redis队列，抢红包时入库异步处理</p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service("redpackServiceImplV6")
public class RedpackServiceImplV6 implements IRedpackService {

    @Autowired
    private RedpackActivityDao activityDao;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;
    @Autowired
    private RedisTemplate<String, RedpackPO> redisTemplate;
    @Autowired
    private RedisTemplate<String, RedpackPersistDTO> persistRedisTemplate;


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

        RedpackPersistDTO persistDTO = new RedpackPersistDTO();

        BeanUtils.copyProperties(po, persistDTO);

        persistDTO.setUserId(dto.getUserId());
        persistDTO.setCreateTime(LocalDateTime.now());

        //异步入库
        this.persistRedisTemplate.opsForList().rightPush(Const.REDIS_PERSIST_QUEUE_NAME, persistDTO);

        return true;
    }

}
