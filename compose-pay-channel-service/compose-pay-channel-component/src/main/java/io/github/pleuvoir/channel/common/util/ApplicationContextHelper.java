package io.github.pleuvoir.channel.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 方便获取 Spring 容器
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static  <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
