//package io.github.pleuvoir.redpack.service;
//
//import io.github.pleuvoir.redpack.BaseTest;
//import io.github.pleuvoir.redpack.model.dto.CreateRedpackDTO;
//import io.github.pleuvoir.redpack.model.dto.FightRedpackDTO;
//import io.github.pleuvoir.redpack.model.dto.ResultMessageDTO;
//import io.github.pleuvoir.redpack.service.internal.ICreateRedpackService;
//import io.github.pleuvoir.redpack.service.internal.IFightRedpackDelegate;
//import org.flywaydb.core.Flyway;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
// */
//public class SingleTest extends BaseTest {
//
//
//    @Autowired
//    protected Flyway flyway;
//    @Autowired
//    protected IRedpackAgent redpackAgent;
//
//    @Autowired
//    private ICreateRedpackService createRedpackService;
//
//    protected List<Long> userList = new ArrayList<Long>();
//
//    Long redpackId; //红包编号
//
//    @Qualifier("fightRedpackDelegateV3")
//    @Autowired
//    IFightRedpackDelegate delegate;
//
//    @Before
//    public void prepareData() {
//        //生成用户
//        long l = ThreadLocalRandom.current().nextLong(1000);
//        for (int i = 0; i < 1000; i++) {
//            userList.add(l);
//        }
//    }
//
//    @Test
//    public void test() throws InterruptedException {
//        //清空表数据
//        flyway.clean();
//        flyway.migrate();
//
//        // 发放一个红包
//        CreateRedpackDTO redpackDTO = new CreateRedpackDTO();
//        redpackDTO.setTotal(1000);
//        redpackDTO.setTotalAmount(new BigDecimal("100"));
//        redpackDTO.setUserId(1L);
//        this.redpackId = createRedpackService.create(redpackDTO).getRedpackId(); // 红包编号
//
//        redpackAgent.setIFightRedpackDelegate(delegate);
//
//
//        Long userId = this.userList.get(Math.abs(new Random().nextInt() % this.userList.size()));
//        FightRedpackDTO fightRedpackDTO = new FightRedpackDTO();
//        fightRedpackDTO.setUserId(userId);
//        fightRedpackDTO.setRedpackId(redpackId);
//        ResultMessageDTO<Boolean> resultMessageDTO = redpackAgent.fight(fightRedpackDTO);
//        if (resultMessageDTO.isSuccess()) {
//            Boolean flag = resultMessageDTO.getData();
//            if (flag) {
//                System.out.println("ok");
//            } else {
//                System.out.println("fail");
//            }
//        } else {
//            // 并发异常
//            System.out.println("error");
//        }
//    }
//}
