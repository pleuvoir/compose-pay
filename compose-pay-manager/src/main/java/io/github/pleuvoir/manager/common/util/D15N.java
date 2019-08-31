package io.github.pleuvoir.manager.common.util;

import org.apache.commons.lang3.StringUtils;
/**
 * 脱敏工具
 * @author abeir
 *
 */
public class D15N {
	
	public static final String HIDE_CHAR = StringUtils.repeat("*", 6);
	
	/**
	 * 
	 * @return
	 */
	/**
	 * 脱敏部分字符串
	 * @param original 原始字符串
	 * @param excludeLeftLength 左侧保留位数
	 * @param excludeRightLength 右侧保留位数
	 * @return 返回脱敏的字符串
	 * @throws IllegalArgumentException 当保留的总位数大于原始字符串长度时抛出
	 */
	public static String hide(String original, int excludeLeftLength, int excludeRightLength) throws IllegalArgumentException{
		if(StringUtils.isBlank(original)) {
			return original;
		}
		if(StringUtils.length(original) < (excludeLeftLength + excludeRightLength)) {
			return HIDE_CHAR;
//			throw new IllegalArgumentException("原始字符串，保留位数超过字符串的长度: " + original.length() + "，left："+excludeLeftLength+"，right："+excludeRightLength);
		}
		String left = StringUtils.left(original, excludeLeftLength);
		String right = StringUtils.right(original, excludeRightLength);
		return new StringBuilder(left).append(HIDE_CHAR).append(right).toString();
	}

	/**
	 * 保留左侧3位右侧3位，其他部分以"*"代替
	 * @param original
	 * @return
	 */
	public static String left3Right3(String original) {
		return hide(original, 3, 3);
	}
}
