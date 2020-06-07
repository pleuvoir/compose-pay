package io.github.pleuvoir;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.pleuvoir.gateway.PayGatewayLauncher;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("rd")
@SpringBootTest(classes = PayGatewayLauncher.class)
public abstract class BaseTest {

}
