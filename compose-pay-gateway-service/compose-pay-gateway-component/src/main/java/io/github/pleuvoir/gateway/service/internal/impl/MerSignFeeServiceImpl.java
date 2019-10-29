package io.github.pleuvoir.gateway.service.internal.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.pleuvoir.gateway.dao.mer.MerSignFeeDao;
import io.github.pleuvoir.gateway.model.po.MerSignFeePO;
import io.github.pleuvoir.gateway.service.internal.MerSignFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户签约支付产品
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Service
public class MerSignFeeServiceImpl implements MerSignFeeService {

    @Autowired
    private MerSignFeeDao merSignFeeDao;

    @Override
    public MerSignFeePO getByMidAndPayProductCode(String mid, String payProductCode) {
        MerSignFeePO query = new MerSignFeePO();
        query.setMid(mid);
        query.setPayProduct(payProductCode);
        return merSignFeeDao.selectOne(query);
    }

    @Override
    public MerSignFeePO getMerSignsByMidAndPayWayAndPayType(String mid, String payType, String payWay) {
        MerSignFeePO query = new MerSignFeePO();
        query.setMid(mid);
        query.setPayType(payType);
        query.setPayWay(payWay);
        return merSignFeeDao.selectOne(query);
    }

}
