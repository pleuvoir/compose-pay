package io.github.pleuvoir.redpack.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Condition;
import io.github.pleuvoir.redpack.BaseTest;
import io.github.pleuvoir.redpack.model.po.RedpackPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class RedpackDaoTest extends BaseTest {

    @Autowired
    private RedpackDao dao;

    @Test
    public void test() {

        RedpackPO redpackPO = new RedpackPO();
        redpackPO.setStatus(1);

        dao.insert(redpackPO);


        List<RedpackPO> redpackPOS = dao.selectList(Condition.empty());
        System.out.println(JSON.toJSONString(redpackPOS, true));
    }
}
