package io.github.pleuvoir.gateway.constants;

import lombok.Getter;

/**
 * 业务结果码
 * @author pleuvoir
 * 
 */
public enum ResultCodeEnum {

	/** 成功 */
	SUCCESS("SUCCESS", "ok"),
	/** 系统错误 */
	ERROR("ERROR", "系统繁忙，请稍后再试。"),
	/** 验签失败 */
	VERIFY_FAILED("VERIFY_FAILED", "验签失败"),
	/** 缺少参数 */
	LACK_PARAM("LACK_PARAM", "缺少参数"),
	/** 商户不存在 */
	NO_MERCHANT("NO_MERCHANT", "商户不存在"),
	/** 参数错误 */
	PARAM_ERROR("PARAM_ERROR","参数错误"),
	
	;

	@Getter
	private String code;

	@Getter
	private String msg;

	private ResultCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 若参数是字符串，判断字符串是否与code相同<br>
	 * 若参数是ResultCode对象，判断其中的code是否相同
	 * 
	 * @param obj
	 * @return
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
		} else if (obj instanceof ResultCodeEnum) {
			return this.getCode().equals(((ResultCodeEnum) obj).getCode());
		}
		return false;
	}

}
