package io.github.pleuvoir.redpack.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.github.pleuvoir.redpack.common.Const;
import io.github.pleuvoir.redpack.common.RspCode;
import io.github.pleuvoir.redpack.common.utils.IdUtils;
import io.github.pleuvoir.redpack.dao.RedpackActivityDao;
import io.github.pleuvoir.redpack.dao.RedpackDao;
import io.github.pleuvoir.redpack.dao.RedpackDetailDao;
import io.github.pleuvoir.redpack.exception.OptimisticLockException;
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
 * <p>将冲突分散到红包表，在创建活动的时候预先分配红包，利用红包表做乐观锁，触发后多次循环</p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service("redpackServiceImplV4")
public class RedpackServiceImplV4 implements IRedpackService {

    @Autowired
    private RedpackActivityDao activityDao;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager txManager;

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

        for (Integer i = 0; i < dto.getTotal(); i++) {
            RedpackPO redpackPO = new RedpackPO();
            redpackPO.setId(IdUtils.nextId());
            redpackPO.setAmount(decimal);
            redpackPO.setStatus(RedpackPO.ENABLE);
            redpackPO.setActivityId(redpackActivityPO.getId());
            redpackPO.setVersion(0);
            redpackDao.insert(redpackPO);
        }
        return new CreateActivityResultDTO(id);
    }


    @Override
    public boolean fight(FightRedpackDTO dto) throws RedpackException {

        boolean isFail = false;
        int i = 0;

        do {
            isFail = false;
            i++;
            // 开启事物
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = txManager.getTransaction(def);

            RedpackPO query = new RedpackPO();
            query.setStatus(RedpackPO.ENABLE);
            query.setActivityId(dto.getActivityId());

            EntityWrapper<RedpackPO> wrapper = new EntityWrapper<>(query);
            List<RedpackPO> pos = redpackDao.selectList(wrapper);

            if (CollectionUtils.isEmpty(pos)) {
                return false;
            }

            // 随机获取可用的红包，降低冲突
            int rd = ThreadLocalRandom.current().nextInt(pos.size());
            RedpackPO po = pos.get(rd);
            po.setStatus(RedpackPO.DISABLE);

            try {
                //乐观锁更新
                Integer row = redpackDao.updateById(po);
                if (row == 0) {
                    throw new OptimisticLockException();
                }
                RedpackDetailPO detailPO = new RedpackDetailPO();
                detailPO.setAmount(po.getAmount());
                detailPO.setRedpackId(po.getActivityId());
                detailPO.setUserId(dto.getUserId());
                detailDao.insert(detailPO);
                txManager.commit(txStatus);
            } catch (OptimisticLockException e) {
                txManager.rollback(txStatus);
                if (i != Const.OPTIMISTIC_LOCK_RETRY_MAX) {
                    isFail = true;
                } else {
                    throw new RedpackException(RspCode.ERROR);
                }
            } catch (Throwable e) {
                log.error("", e);
                txManager.rollback(txStatus);
                throw new RedpackException(RspCode.ERROR);
            }
        } while (isFail);

        return true;
    }

}
