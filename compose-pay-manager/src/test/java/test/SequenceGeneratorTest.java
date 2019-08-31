package test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import io.github.pleuvoir.manager.common.extension.MysqlSequenceIncrementer;
import io.github.pleuvoir.manager.common.extension.SequenceGenerator;
import io.github.pleuvoir.manager.common.util.ApplicationContextUtils;

/**
 * @author pleuvoir
 * 
 */
public class SequenceGeneratorTest extends BaseTest {

	@Autowired
	private ApplicationContext context;
	
	static int count = 3000;
	static final CyclicBarrier BARRIER = new CyclicBarrier(count);
	
	@Test
	public void test() throws InterruptedException {

		String[] beanDefinitionNames = context.getBeanDefinitionNames();
		
		
		for (String string : beanDefinitionNames) {
			System.out.println(string);
		}
//		MysqlSequenceIncrementer incrementer = ApplicationContextUtils.getBean("pub_sequence#SEQ_MENU_ID",
//				MysqlSequenceIncrementer.class);
//		
//		System.out.println(incrementer);
//		//pub_sequence#SEQ_MENU_ID
		
		
		ExecutorService pool = Executors.newFixedThreadPool(3000);
		 // 普通用法可循环使用，注意不要再不同线程中用
		for (int i = 0; i < count; i++) {
			pool.submit(new ExcuteThread("normal-" + i));
		}
		pool.awaitTermination(30, TimeUnit.SECONDS);
        
	}
	
	static class ExcuteThread extends Thread {

        public ExcuteThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                if (new Random().nextBoolean()) {
                    TimeUnit.SECONDS.sleep(2);
                }
                System.out.println(getName() + " 到达屏障前");
                BARRIER.await();  //第一次使用
                TimeUnit.SECONDS.sleep(1);

                System.out.println("========");
            	String next = SequenceGenerator.next("SEQ_MENU_ID");
            	System.out.println("获取到next=" + next);
            	String TEST = SequenceGenerator.next("TEST");
        		System.out.println("获取到TEST=" + TEST);
        		
            } catch (Exception e) { //BrokenBarrierException代表已经破损，可能无法等待所有线程齐全了
                e.printStackTrace();
            }
        }
    }


}
