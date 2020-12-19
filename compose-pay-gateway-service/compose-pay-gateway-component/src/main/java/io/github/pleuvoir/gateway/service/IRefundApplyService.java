package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.RefundApplyDTO;
import io.github.pleuvoir.gateway.model.dto.RefundApplyResultDTO;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IRefundApplyService {

    RefundApplyResultDTO refundApply(RefundApplyDTO refundApplyDTO) throws BusinessException;
}
