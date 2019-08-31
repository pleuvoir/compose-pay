package io.github.pleuvoir.manager.common.extension;

import io.github.pleuvoir.manager.common.util.ApplicationContextUtils;

public class SequenceGenerator {

	public static String next(String sequenceName) {
		String beanName = "pub_sequence#".concat(sequenceName);
		MysqlSequenceIncrementer incrementer = ApplicationContextUtils.getBean(beanName,
				MysqlSequenceIncrementer.class);
		return incrementer.nextStringValue();
	}

}
