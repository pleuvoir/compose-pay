package io.github.pleuvoir.gateway.constants;

import lombok.Getter;

/**
 * 业务结果码
 * @author pleuvoir
 * 
 */
public enum RspCodeEnum {

	/** 成功 */
	SUCCESS("SUCCESS", "ok"),
	/** 操作失败 */
	FAIL("FAIL", "操作失败"),
	/** 系统错误 */
	ERROR("ERROR", "系统繁忙，请稍后再试。"),
	/** 验签失败 */
	VERIFY_FAILED("VERIFY_FAILED", "验签失败"),
	/** 缺少参数 */
	LACK_PARAM("LACK_PARAM", "缺少参数"),
	/** 商户不存在 */
	NO_MERCHANT("NO_MERCHANT", "商户不存在"),
	/** 无效商户 */
	INVALID_MERCHANT("INVALID_MERCHANT", "无效商户"),
	/** 参数错误 */
	PARAM_ERROR("PARAM_ERROR","参数错误"),
	/** 无效的支付方式*/
	INVALID_PAYTYPE("INVALID_PAYTYPE","无效的支付方式"),


	;

	@Getter
	private String code;

	@Getter
	private String msg;

	private RspCodeEnum(String code, String msg) {
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
		} else if (obj instanceof RspCodeEnum) {
			return this.getCode().equals(((RspCodeEnum) obj).getCode());
		}
		return false;
	}

}
