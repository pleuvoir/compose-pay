package io.github.pleuvoir.redpack.service.impl;

import io.github.pleuvoir.redpack.common.RspCode;
import io.github.pleuvoir.redpack.common.utils.AssertUtil;
import io.github.pleuvoir.redpack.common.utils.IdUtils;
import io.github.pleuvoir.redpack.common.utils.UtilMix;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>利用红包活动表做悲观锁</p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service("redpackServiceImplV3")
public class RedpackServiceImplV3 implements IRedpackService {

    @Autowired
    private RedpackActivityDao activityDao;
    @Autowired
    private RedpackDao redpackDao;
    @Autowired
    protected RedpackDetailDao detailDao;

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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public boolean fight(FightRedpackDTO dto) throws RedpackException {

        RedpackActivityPO activityPO = activityDao.selectByIdForUpdate(dto.getActivityId());
        if (activityPO == null) {
            throw new RedpackException(RspCode.REDPACK_NOT_EXIST);
        }

        //红包剩余金额
        BigDecimal surplusAmount = activityPO.getSurplusAmount();
        Integer surplusTotal = activityPO.getSurplusTotal();

        //红包是否还有剩余金额
        if (BigDecimal.ZERO.compareTo(surplusAmount) > 0 || surplusTotal == 0) {
            return false;
        }
        // TODO 此处应该有个装比的红包生成算法

        BigDecimal amount = BigDecimal.ZERO;

        //如果剩下一次了，那么此次金额就是剩余金额
        if (surplusTotal == 1) {
            amount = surplusAmount;
        } else {
            //否则就在0和剩余金额之间生成一个随机数
            amount = UtilMix.getRedpackAmount(surplusAmount);
        }

        //更新剩余金额和总数 悲观锁更新
        activityPO.setSurplusTotal(activityPO.getSurplusTotal() - 1);
        activityPO.setSurplusAmount(surplusAmount.subtract(amount));
        Integer row = activityDao.updateById(activityPO);
        AssertUtil.assertOne(row, "更新红包活动失败");

        RedpackPO redpackPO = new RedpackPO();
        Long id = IdUtils.nextId();
        redpackPO.setId(id);
        redpackPO.setAmount(amount);
        redpackPO.setActivityId(activityPO.getId());
        redpackDao.insert(redpackPO);

        RedpackDetailPO detailPO = new RedpackDetailPO();
        detailPO.setAmount(amount);
        detailPO.setRedpackId(redpackPO.getId());
        detailPO.setUserId(dto.getUserId());
        detailDao.insert(detailPO);

        return true;
    }

}
