package io.github.pleuvoir.manager.common.taglib;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import io.github.pleuvoir.manager.common.util.D15N;

/**
 * 卡券码脱敏
 * @author abeir
 *
 */
public class D15nTicketTag extends RequestContextAwareTag {
	private static final long serialVersionUID = -721294476088133178L;
	
	private String value;		//需要脱敏的值
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(StringUtils.isBlank(getValue())) {
			return 0;
		}
		String d15nValue = D15N.HIDE_CHAR;
		try{
			d15nValue = D15N.left3Right3(getValue());
		}catch (IllegalArgumentException e){}
		pageContext.getOut().write(d15nValue);
		return 0;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
