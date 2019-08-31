package io.github.pleuvoir.manager.common.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成uuid并去掉 "-"
 * @author abeir
 *
 */
public class Generator {
	/**
	 * 横线
	 */
	public static final String HORIZONTAL = "-";
	/**
	 * 生成下一个UUID
	 * @return
	 */
	public static String nextUUID(){
		return UUID.randomUUID().toString().replace(HORIZONTAL, StringUtils.EMPTY);
	}
}
