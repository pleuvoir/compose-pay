package io.github.pleuvoir.gateway.model.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * 
 * @author pleuvoir
 * 
 */
@Data
public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = -1699831686561099489L;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
