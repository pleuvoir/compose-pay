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

import io.github.pleuvoir.gateway.common.Const;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.service.internal.BaseService;
import io.github.pleuvoir.gateway.service.internal.MerSignFeeService;
import io.github.pleuvoir.gateway.service.internal.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础服务实现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Service
public class BaseServiceImpl implements BaseService {


    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerSignFeeService signFeeService;

    @Cacheable(key = Const.CACHE_KEY_EXPRESSION_MERCHANT, value = "0")
    @Override
    public MerchantPO getMerchant(String mid) {
        return merchantService.getByMid(mid);
    }

    @Override
    public MerSignFeePO getMerSignsByMidAndPayProductCode(String mid, String payProductCode) {
        return signFeeService.getByMidAndPayProductCode(mid, payProductCode);
    }

    @Override
    public MerSignFeePO getMerSignsByMidAndPayWayAndPayType(String mid, String payType, String payWay) {
        return signFeeService.getMerSignsByMidAndPayWayAndPayType(mid, payType, payWay);
    }

    /**
     * 检查商户信息<br>
     * <ol>
     *     <li>商户是否存在</li>
     *     <li>商户状态是否正常</li>
     * </ol>
     *
     * @param mid 商户号
     */
    protected MerchantPO checkMerchant(String mid) throws BusinessException {
        if (StringUtils.isBlank(mid)) {
            log.warn("-=- 检查商户信息，参数错误，mid为空。");
            throw new BusinessException(ResultCodeEnum.LACK_PARAM);
        }
        MerchantPO merchant = this.getMerchant(mid);
        if (merchant == null) {
            throw new BusinessException(ResultCodeEnum.NO_MERCHANT);
        }
        if (!MerchantPO.STATUS_NORMAL.equals(merchant.getStatus())) {
            log.warn("-=- 检查商户信息，商户状态异常，mid={}", mid);
            throw new BusinessException(ResultCodeEnum.INVALID_MERCHANT);
        }
        return merchant;
    }

    /**
     * 检查商户是否签约了该支付产品
     *
     * @param mid     商户号
     * @param payType 支付种类
     * @param payWay  支付方式
     */
    protected MerSignFeePO checkMerSignFee(String mid, String payType, String payWay) throws BusinessException {
        MerSignFeePO merSignFeePO = getMerSignsByMidAndPayWayAndPayType(mid, payType, payWay);
        if (merSignFeePO == null) {
            log.warn("商户{}未签约{}支付种类和{}支付方式", mid, payType, payWay);
            throw new BusinessException(ResultCodeEnum.MER_UN_SIGN_ERROR);
        }
        return merSignFeePO;
    }
}
