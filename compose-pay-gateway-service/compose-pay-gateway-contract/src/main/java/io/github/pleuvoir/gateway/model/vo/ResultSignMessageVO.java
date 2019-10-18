package io.github.pleuvoir.gateway.model.vo;

import io.github.pleuvoir.gateway.constants.MessageCodeEnum;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自动增加签名的实体，返回此对象时切面会自动增加签名
 * @author pleuvoir
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResultSignMessageVO extends ResultMessageVO {

	private static final long serialVersionUID = 708919751285984539L;

	private String resultCode; // 业务结果代码

	private String noise; // 随机字符串

	private String mid; // 商户号

	private String sign; // 签名

	private String payType; // 支付

	public ResultSignMessageVO(ResultCodeEnum resultCode, String mid) {
		super(MessageCodeEnum.SUCCESS, resultCode);
		this.mid = mid;
		this.resultCode = resultCode.getCode();
	}

	public ResultSignMessageVO(ResultCodeEnum resultCode, String mid, String msg) {
		super(MessageCodeEnum.SUCCESS, resultCode, msg);
		this.mid = mid;
		this.resultCode = ResultCodeEnum.ERROR.getCode();
	}
}
