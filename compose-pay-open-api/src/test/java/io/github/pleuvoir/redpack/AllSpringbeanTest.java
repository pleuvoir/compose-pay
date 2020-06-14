package io.github.pleuvoir.redpack;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class AllSpringbeanTest extends BaseTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void test(){
        for (String definitionName : context.getBeanDefinitionNames()) {

            System.out.println(definitionName);
        }
    }
}
