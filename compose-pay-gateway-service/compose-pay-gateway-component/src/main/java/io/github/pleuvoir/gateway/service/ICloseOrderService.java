package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.CloseOrderDTO;
import io.github.pleuvoir.gateway.model.dto.CloseOrderResultDTO;
import io.github.pleuvoir.gateway.model.vo.Result;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface ICloseOrderService {

    CloseOrderResultDTO closeOrder(CloseOrderDTO closeOrderDTO) throws BusinessException;

}
