package io.github.pleuvoir.redpack.service;

import io.github.pleuvoir.redpack.BaseTest;
import io.github.pleuvoir.redpack.exception.RedpackException;
import io.github.pleuvoir.redpack.model.dto.CreateActivityDTO;
import io.github.pleuvoir.redpack.model.dto.CreateActivityResultDTO;
import io.github.pleuvoir.redpack.model.dto.FightRedpackDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class RedpackServiceTest extends BaseTest {

    @Qualifier("redpackServiceImplV5")
    @Autowired
    private IRedpackService redpackService;


    @Test
    public void testCreate() throws RedpackException {

        int count = 5;
        CreateActivityDTO activityDTO = new CreateActivityDTO();
        activityDTO.setTotal(count);
        activityDTO.setTotalAmount(BigDecimal.TEN);
        activityDTO.setUserId(1L);

        CreateActivityResultDTO createActivityResultDTO = redpackService.create(activityDTO);

        for (int i = 0; i < count; i++) {
            FightRedpackDTO fightRedpackDTO = new FightRedpackDTO();
            fightRedpackDTO.setUserId(System.currentTimeMillis());
            fightRedpackDTO.setActivityId(createActivityResultDTO.getActivityId());
            redpackService.fight(fightRedpackDTO);
        }

    }

//    @Test
//    public void testFight() throws RedpackException, InterruptedException {
//        for (int i = 0; i < 5; i++) {
//            FightRedpackDTO fightRedpackDTO = new FightRedpackDTO();
//            fightRedpackDTO.setUserId(System.currentTimeMillis());
//            fightRedpackDTO.setRedpackId(1202201329779363842L);
//            redpackService.fight(fightRedpackDTO);
//            TimeUnit.SECONDS.sleep(1);
//        }
//    }
}
