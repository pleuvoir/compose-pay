package io.github.pleuvoir.gateway.service.internal.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.pleuvoir.gateway.dao.mer.MerChannelDao;
import io.github.pleuvoir.gateway.dao.mer.MerChantDao;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.service.internal.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private MerChannelDao merChannelDao;

    @Override
    public MerChannelPO find(String mid, String productCode) {

        log.info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=渠道路由开始-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        MerChannelPO query = new MerChannelPO();
        query.setMid(mid);
        query.setProductCode(productCode);
        query.setIsPay(MerChannelPO.CAN_PAY);

        EntityWrapper<MerChannelPO> wrapper = Condition.wrapper();
        wrapper.setEntity(query);

        final List<MerChannelPO> merCnlList = merChannelDao.selectList(wrapper);


        MerChannelPO merChannelPO = maxPriority(merCnlList);

        log.info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=渠道路由结束-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        return merChannelPO;
    }


    /**
     * 选取优先级最高的
     */
    private MerChannelPO maxPriority(List<MerChannelPO> merCnlList) {
        if (CollectionUtils.isEmpty(merCnlList)) {
            return null;
        }
        //找到优先级最高的
        MerChannelPO maxMerCnl = null;
        int maxPriority = 0;
        for (int i = 0; i < merCnlList.size(); i++) {
            if (i == 0) {
                maxMerCnl = merCnlList.get(i);
                maxPriority = maxMerCnl.getPriority() == null ? Integer.MIN_VALUE : maxMerCnl.getPriority().intValue();
                continue;
            }
            int priority = merCnlList.get(i).getPriority() == null ? Integer.MIN_VALUE : merCnlList.get(i).getPriority();
            if (maxPriority < priority) {
                maxMerCnl = merCnlList.get(i);
                maxPriority = priority;
            }
        }
        return maxMerCnl;
    }
}
