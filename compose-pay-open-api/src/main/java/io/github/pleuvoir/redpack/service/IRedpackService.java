package io.github.pleuvoir.redpack.service;

import io.github.pleuvoir.redpack.exception.RedpackException;
import io.github.pleuvoir.redpack.model.dto.CreateActivityDTO;
import io.github.pleuvoir.redpack.model.dto.CreateActivityResultDTO;
import io.github.pleuvoir.redpack.model.dto.FightRedpackDTO;

/**
 * 红包服务
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IRedpackService {


    /**
     * 创建一个红包
     */
    CreateActivityResultDTO create(CreateActivityDTO dto);

    /**
     * 抢红包
     */
    boolean fight(FightRedpackDTO dto) throws RedpackException;
}
