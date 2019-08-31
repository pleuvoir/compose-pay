package io.github.pleuvoir.manager.common.taglib;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 格式化日期，支持Java类型：{@link java.util.Date} {@link java.time.LocalDate} {@link java.time.LocalDateTime}
 */
public class FormatDateTag extends RequestContextAwareTag {

	private Object value;		//日期值

	private String partten;		//格式化字符串

	@Override
	protected int doStartTagInternal() throws Exception {

		String dateString = StringUtils.EMPTY;
		if(value instanceof Date){
			dateString = fmtDate((Date) value, partten);
		}else if(value instanceof LocalDate){
			dateString = fmtLocalDate((LocalDate) value, partten);
		}else if(value instanceof LocalDateTime){
			dateString = fmtLocalDateTime((LocalDateTime) value, partten);
		}
		pageContext.getOut().write(dateString);
		return 0;
	}

	private String fmtDate(Date date, String partten){
		return new SimpleDateFormat(partten).format(date);
	}

	private String fmtLocalDate(LocalDate date, String partten){
		return DateTimeFormatter.ofPattern(partten).format(date);
	}

	private String fmtLocalDateTime(LocalDateTime date, String partten){
		return DateTimeFormatter.ofPattern(partten).format(date);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getPartten() {
		return partten;
	}

	public void setPartten(String partten) {
		this.partten = partten;
	}
}
