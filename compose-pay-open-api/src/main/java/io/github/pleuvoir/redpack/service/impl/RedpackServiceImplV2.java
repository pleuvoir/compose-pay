package io.github.pleuvoir.redpack.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import io.github.pleuvoir.redpack.common.Const;
import io.github.pleuvoir.redpack.common.RspCode;
import io.github.pleuvoir.redpack.common.utils.IdUtils;
import io.github.pleuvoir.redpack.common.utils.UtilMix;
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
 * <p>利用红包活动表做乐观锁，触发后会多次重试</p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service("redpackServiceImplV2")
public class RedpackServiceImplV2 implements IRedpackService {

    @Autowired
    private RedpackActivityDao activityDao;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager txManager;

    @Override
    public CreateActivityResultDTO create(CreateActivityDTO dto) {
        RedpackActivityPO redpackActivityPO = new RedpackActivityPO();
        redpackActivityPO.setVersion(0);
        redpackActivityPO.setSurplusAmount(dto.getTotalAmount());
        redpackActivityPO.setTotalAmount(dto.getTotalAmount());
        redpackActivityPO.setTotal(dto.getTotal());
        redpackActivityPO.setSurplusTotal(dto.getTotal());
        Long id = IdUtils.nextId();
        redpackActivityPO.setId(id);
        activityDao.insert(redpackActivityPO);
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

            try {
                RedpackActivityPO activityQuery = new RedpackActivityPO();
                activityQuery.setId(dto.getActivityId());
                RedpackActivityPO activityPO = activityDao.selectOne(activityQuery);
                if (activityPO == null) {
                    throw new RedpackException(RspCode.REDPACK_NOT_EXIST);
                }

                // 红包剩余金额
                BigDecimal surplusAmount = activityPO.getSurplusAmount();
                Integer surplusTotal = activityPO.getSurplusTotal();

                // 红包是否还有剩余金额
                if (BigDecimal.ZERO.compareTo(surplusAmount) > 0 || surplusTotal == 0) {
                    return false;
                }
                // TODO 此处应该有个装比的红包生成算法

                BigDecimal amount = BigDecimal.ZERO;

                // 如果剩下一次了，那么此次金额就是剩余金额
                if (surplusTotal == 1) {
                    amount = surplusAmount;
                } else {
                    // 否则就在0和剩余金额之间生成一个随机数
                    amount = UtilMix.getRedpackAmount(surplusAmount);
                }

                // 更新剩余金额和总数
                activityPO.setSurplusTotal(activityPO.getSurplusTotal() - 1);
                activityPO.setSurplusAmount(surplusAmount.subtract(amount));
                Integer row = activityDao.updateById(activityPO);
                if (row == 0) {
                    throw new OptimisticLockException();
                }

                RedpackPO redpackPO = new RedpackPO();
                redpackPO.setId(IdUtils.nextId());
                redpackPO.setAmount(amount);
                redpackPO.setActivityId(activityPO.getId());
                redpackDao.insert(redpackPO);

                RedpackDetailPO detailPO = new RedpackDetailPO();
                detailPO.setAmount(amount);
                detailPO.setRedpackId(redpackPO.getId());
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
