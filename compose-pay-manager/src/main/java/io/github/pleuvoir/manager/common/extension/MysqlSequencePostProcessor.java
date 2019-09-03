package io.github.pleuvoir.manager.common.extension;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.Condition;

import io.github.pleuvoir.manager.dao.pub.PubSequenceDao;
import io.github.pleuvoir.manager.model.po.pub.PubSequencePO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MysqlSequencePostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;

		PubSequenceDao sequenceDao = beanFactory.getBean(PubSequenceDao.class);
		DataSource dataSource = beanFactory.getBean(DataSource.class);

		List<PubSequencePO> all = sequenceDao.selectList(Condition.empty());

		all.forEach(s -> {

			log.debug("【序列自增器】开始注册{}", s);

			BeanDefinitionBuilder definition = BeanDefinitionBuilder
					.genericBeanDefinition(MysqlSequenceIncrementer.class, () -> {
						MysqlSequenceIncrementer incrementer = new MysqlSequenceIncrementer();
						incrementer.setCacheSize(s.getCacheSize());
						incrementer.setDataSource(dataSource);
						incrementer.setIncrementerName("pub_sequence"); // 公共序列表
						incrementer.setColumnName("current_id");
						incrementer.setSequenceName(s.getSequenceName());
						incrementer.setPaddingLength(s.getPaddingLength());
						return incrementer;
					});

			listableBeanFactory.registerBeanDefinition("pub_sequence#".concat(s.getSequenceName()),
					definition.getBeanDefinition());
		});
	}

}
