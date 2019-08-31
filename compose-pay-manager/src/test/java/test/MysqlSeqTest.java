package test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import io.github.pleuvoir.manager.common.extension.MysqlSequenceIncrementer;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试序列
 * 
 * @author pleuvoir
 * 
 */
@Slf4j
public class MysqlSeqTest extends BaseTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void test() throws InterruptedException {
		MysqlSequenceIncrementer pub_menu = applicationContext.getBean("pub_sequence#pub_menu",
				MysqlSequenceIncrementer.class);
		
		MysqlSequenceIncrementer t_pay = applicationContext.getBean("pub_sequence#t_pay",
				MysqlSequenceIncrementer.class);

		for (int i = 0; i < 10; i++) {
			log.info("pub_menu next val = {}", pub_menu.nextStringValue());
			log.info("t_pay next val = {}", t_pay.nextStringValue());
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
