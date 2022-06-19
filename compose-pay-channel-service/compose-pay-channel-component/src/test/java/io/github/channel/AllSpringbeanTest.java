package io.github.channel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class AllSpringbeanTest extends BaseTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void test() {
        for (String definitionName : context.getBeanDefinitionNames()) {
            System.out.println(definitionName);
        }
    }
}
