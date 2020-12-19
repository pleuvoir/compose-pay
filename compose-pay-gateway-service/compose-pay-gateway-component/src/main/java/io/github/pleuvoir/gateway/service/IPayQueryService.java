package io.github.pleuvoir.gateway.service;

import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.dto.PayQueryDTO;
import io.github.pleuvoir.gateway.model.dto.PayQueryResultDTO;
import io.github.pleuvoir.gateway.model.vo.Result;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IPayQueryService {

    PayQueryResultDTO payQuery(PayQueryDTO payQueryDTO) throws BusinessException;

}
