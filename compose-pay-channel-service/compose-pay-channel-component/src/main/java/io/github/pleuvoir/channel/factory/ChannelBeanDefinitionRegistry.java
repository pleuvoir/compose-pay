//package io.github.pleuvoir.channel.factory;
//
//import io.github.pleuvoir.channel.common.annotation.ChannelService;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.stereotype.Component;
//
///**
// * 通道实现类别名注册
// *
// * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
// */
//@Component
//public class ChannelBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        String[] beans = beanFactory.getBeanNamesForAnnotation(ChannelService.class);
//        if (beans.length > 0) {
//            for (String bean : beans) {
//                Class<?> clazz = beanFactory.getType(bean);
//                ChannelService channelService = clazz.getAnnotation(ChannelService.class);
//                String channelName = channelService.channel().name();
//                String serviceType = channelService.serviceTypeEnum().getId();
//                String beanName = channelName + serviceType + "Service";
//
//                //防止别名覆盖bean的ID
//                if (beanFactory.containsBeanDefinition(beanName)) {
//                    return;
//                } else {
//                    beanFactory.registerAlias(bean, beanName);
//                }
//
//            }
//        }
//    }
//}
