/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.gateway.service.internal.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.pleuvoir.gateway.dao.mer.MerChannelDao;
import io.github.pleuvoir.gateway.model.po.MerChannelPO;
import io.github.pleuvoir.gateway.service.internal.MerChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class MerChannelServiceImpl implements MerChannelService {

    @Autowired
    private MerChannelDao merChannelDao;

    @Override
    public List<MerChannelPO> getUsableMerCnlList(String mid, String payType, String payWay) {
        MerChannelPO query = new MerChannelPO();
        query.setMid(mid);
        query.setPayType(payType);
        query.setPayWay(payWay);
        query.setIsPay(MerChannelPO.CAN_PAY);
        EntityWrapper<MerChannelPO> wrapper = Condition.wrapper();
        wrapper.setEntity(query);
        final List<MerChannelPO> merCnlList = merChannelDao.selectList(wrapper);
        return merCnlList;
    }

    @Override
    public MerChannelPO maxPriority(String mid, String payType, String payWay) {
        return maxPriority(this.getUsableMerCnlList(mid, payType, payWay));
    }

    /**
     * 选取优先级最高的
     */
    public MerChannelPO maxPriority(List<MerChannelPO> merCnlList) {
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
