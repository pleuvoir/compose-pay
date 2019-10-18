package io.github.pleuvoir.gateway.model.vo;

import io.github.pleuvoir.gateway.constants.ResultCodeEnum;

/**
 * @author pleuvoir
 * 
 */
public class ResultBasePayVO extends ResultSignMessageVO {

	private static final long serialVersionUID = 5566661845252721918L;

	public ResultBasePayVO(ResultCodeEnum resultCode, String mid, String msg) {
		super(resultCode, mid, msg);
	}

	public ResultBasePayVO(ResultCodeEnum resultCode, String mid) {
		super(resultCode, mid);
	}

}
