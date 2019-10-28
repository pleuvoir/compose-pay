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
    public List<MerSignFeePO> getByMidAndPayProductCode(String mid, String payProductCode) {

        MerSignFeePO query = new MerSignFeePO();
        query.setMid(mid);
        query.setPayProduct(payProductCode);

        EntityWrapper<MerSignFeePO> wrapper = Condition.wrapper();
        wrapper.setEntity(query);

        List<MerSignFeePO> signFeePOList = merSignFeeDao.selectList(wrapper);
        return signFeePOList;
    }

}
