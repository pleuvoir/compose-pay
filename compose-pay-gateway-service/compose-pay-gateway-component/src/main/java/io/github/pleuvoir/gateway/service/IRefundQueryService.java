package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.RefundQueryDTO;
import io.github.pleuvoir.gateway.model.dto.RefundQueryResultDTO;
import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IRefundQueryService {

    List<RefundQueryResultDTO> refundQuery(RefundQueryDTO refundQueryDTO) throws BusinessException;
}
