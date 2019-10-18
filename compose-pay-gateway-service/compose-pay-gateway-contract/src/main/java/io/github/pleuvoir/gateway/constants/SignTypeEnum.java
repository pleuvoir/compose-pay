package io.github.pleuvoir.gateway.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * 对外接口的签名类型
 * @author pleuvoir
 *
 */
public enum SignTypeEnum {
	/** MD5签名方式 */
	MD5("md5");

	private String type;

	private SignTypeEnum(String type) {
		this.type = type;
	}

	/**
	 * 通过字符串签名类型的获取枚举
	 * @return 若不是有效的签名类型返回null
	 */
	public static SignTypeEnum get(String type) {
		if (StringUtils.isBlank(type)) {
			return null;
		}
		type = type.trim();
		SignTypeEnum[] types = SignTypeEnum.values();
		for (SignTypeEnum t : types) {
			if (type.equalsIgnoreCase(t.toString())) {
				return t;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return type;
	}
}
