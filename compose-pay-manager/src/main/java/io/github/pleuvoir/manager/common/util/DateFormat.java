package io.github.pleuvoir.manager.common.util;

import java.text.SimpleDateFormat;

public enum DateFormat {
	/** 默认时间日期格式：yyyy-MM-dd HH:mm:ss */
	DATETIME_DEFAULT("yyyy-MM-dd HH:mm:ss"),
	/** 自定义日期格式：yyyy-MM-dd HH:mm */
	NOT_SS_DEFAULT("yyyy-MM-dd HH:mm"),
	/** 默认日期格式：yyyy-MM-dd */
	DATE_DEFAULT("yyyy-MM-dd"),
	/** 默认时间格式：HH:mm:ss */
	TIME_DEFAULT("HH:mm:ss"),
	/** 紧凑的日期时间格式：yyyyMMddHHmmss */
	DATETIME_COMPACT("yyyyMMddHHmmss"),
	/** 紧凑的日期格式：yyyyMMdd */
	DATE_COMPACT("yyyyMMdd"),
	/** 带毫秒的时间格式：yyyy-MM-dd HH:mm:ss:SSS */
	DATETIME_MILLISECOND("yyyy-MM-dd HH:mm:ss:SSS"),
	/** 紧凑的带毫秒的时间格式：yyyyMMddHHmmssSSS */
	DATETIME_MILLISECOND_COMPACT("yyyyMMddHHmmssSSS"),
	/** 紧凑的时间格式：HHmmss */
	TIME_COMPACT("HHmmss");
	
	
	private String partten;
	
	private DateFormat(String partten){
		this.partten = partten;
	}
	
	/**
	 * 获取格式字符串
	 * @return
	 */
	public String getPartten(){
		return this.partten;
	}
	
	/**
	 * 获取一个新的{@link SimpleDateFormat}对象
	 * @return
	 */
	public SimpleDateFormat get(){
		return new SimpleDateFormat(this.partten);
	}
}
