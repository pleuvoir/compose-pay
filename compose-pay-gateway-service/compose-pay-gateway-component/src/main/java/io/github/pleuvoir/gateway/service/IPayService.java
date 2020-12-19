package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PayRequestDTO;
import io.github.pleuvoir.gateway.model.dto.PayRequestResultDTO;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IPayService {

    PayRequestResultDTO pay(PayRequestDTO payRequestDTO) throws BusinessException;

}
