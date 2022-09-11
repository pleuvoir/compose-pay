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

import io.github.pleuvoir.gateway.dao.mer.MerIpDao;
import io.github.pleuvoir.gateway.model.po.MerIpPO;
import io.github.pleuvoir.gateway.service.internal.IMerIpService;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import io.github.pleuvoir.pay.common.exception.BusinessException;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * IP服务
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Service
public class MerIpServiceIImpl implements IMerIpService {

    @Resource
    private MerIpDao merIpDao;

    @Override
    public void ipLimit(String mid, String ip) throws BusinessException {
        MerIpPO query = new MerIpPO();
        query.setMid(mid);
        query.setIp(ip);
        MerIpPO merIpPO = merIpDao.selectOne(query);
        if (merIpPO == null) {
            throw new BusinessException(ResultCodeEnum.IP_NO_BIND);
        }
    }
}
