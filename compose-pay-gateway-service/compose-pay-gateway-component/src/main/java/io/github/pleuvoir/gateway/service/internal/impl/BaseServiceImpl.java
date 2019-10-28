package io.github.pleuvoir.gateway.service.internal.impl;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.gateway.common.Const;
import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerchantPO;
import io.github.pleuvoir.gateway.service.internal.BaseService;
import io.github.pleuvoir.gateway.service.internal.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    @Cacheable(key = Const.CACHE_KEY_EXPRESSION_MERCHANT, value = "0")
    @Override
    public MerchantPO getMerchant(String mid) {
        return merchantService.getByMid(mid);
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
            throw new BusinessException(RspCodeEnum.LACK_PARAM);
        }
        MerchantPO merchant = this.getMerchant(mid);
        if (merchant == null) {
            throw new BusinessException(RspCodeEnum.NO_MERCHANT);
        }
        if (!MerchantPO.STATUS_NORMAL.equals(merchant.getStatus())) {
            log.warn("-=- 检查商户信息，商户状态异常，mid={}", mid);
            throw new BusinessException(RspCodeEnum.INVALID_MERCHANT);
        }
        return merchant;
    }
}
