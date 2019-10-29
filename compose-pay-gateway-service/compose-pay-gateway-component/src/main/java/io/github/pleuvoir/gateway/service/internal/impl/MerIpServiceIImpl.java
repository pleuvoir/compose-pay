package io.github.pleuvoir.gateway.service.internal.impl;

import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import io.github.pleuvoir.gateway.dao.mer.MerIpDao;
import io.github.pleuvoir.gateway.exception.BusinessException;
import io.github.pleuvoir.gateway.model.po.MerIpPO;
import io.github.pleuvoir.gateway.service.internal.MerIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class MerIpServiceIImpl implements MerIpService {

    @Autowired
    private MerIpDao merIpDao;

    @Override
    public void ipLimit(String mid, String ip) throws BusinessException {
        MerIpPO query = new MerIpPO();
        query.setMid(mid);
        query.setIp(ip);
        MerIpPO merIpPO = merIpDao.selectOne(query);
        if (merIpPO == null) {
            throw new BusinessException(RspCodeEnum.IP_NO_BIND);
        }
    }
}
