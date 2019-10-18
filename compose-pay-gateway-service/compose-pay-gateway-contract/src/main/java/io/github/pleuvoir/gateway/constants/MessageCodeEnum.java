package io.github.pleuvoir.gateway.constants;

import lombok.Getter;

/**
 * 返回码
 * @author pleuvoir
 *
 */
public enum MessageCodeEnum {
	
	/** 成功 */
	SUCCESS("SUCCESS", "ok"),
	/** 失败 */
	FAIL("FAIL", "交易失败"),
	/** 系统异常 */
	ERROR("ERROR", "系统异常");

	@Getter
	private String code;
	@Getter
	private String msg;

	private MessageCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 若参数是字符串，判断字符串是否与code相同<br>
	 * 若参数是ResultCode对象，判断其中的code是否相同
	 */
	public boolean isEquals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			return this.getCode().equals((String) obj);
		} else if (obj instanceof MessageCodeEnum) {
			return this.getCode().equals(((MessageCodeEnum) obj).getCode());
		}
		return false;
	}

}
