/*
 * Copyright © 2020 pleuvoir (pleuvoir@foxmail.com)
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
package io.github.pleuvoir.gateway.component;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.gateway.agent.BasePayAgent;
import io.github.pleuvoir.gateway.common.aop.MethodTimeLog;
import io.github.pleuvoir.gateway.constants.PayTypeEnum;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.QrCodePayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.QrCodePayResultDTO;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;
import io.github.pleuvoir.gateway.service.IQrCodePayService;
import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author pleuvoir
 */
@Slf4j
@Service
public class BasePayAgentImpl implements BasePayAgent {

  @Resource
  private IQrCodePayService qrCodePayService;

  @MethodTimeLog("获取支付二维码")
  @Override
  public ResultMessageVO<QrCodePayResultDTO> payCode(@Valid QrCodePayRequestDTO dto) {

    if (!this.validatePayType(dto.getPayType())) {
      return ResultMessageVO.fail(ResultCodeEnum.INVALID_PAY_TYPE);
    }

    QrCodePayResultDTO result = null;
    try {
      result = qrCodePayService.qrCodePayUrl(dto);
      if (result != null) {
        log.info("获取支付二维码响应参数：{}", result);
      } else {
        log.error("获取二维码响应参数：null，ResultCodeEnum：{}", JSON.toJSONString(dto));
        return ResultMessageVO.fail(ResultCodeEnum.ERROR);
      }
    } catch (BusinessException e) {
      log.warn("获取支付二维码业务异常，{}", e.getMessage());
      return ResultMessageVO.fail(e.getResultCode());
    }

    return ResultMessageVO.success(result);
  }

  /**
   * 判断支付种类是否正确
   */
  private boolean validatePayType(String payType) {
    if (StringUtils.equals(PayTypeEnum.TYPE_ALIPAY.getName(), payType)
        || StringUtils.equals(PayTypeEnum.TYPE_WECHAT.getName(), payType)) {
      return true;
    }
    return false;
  }
}
