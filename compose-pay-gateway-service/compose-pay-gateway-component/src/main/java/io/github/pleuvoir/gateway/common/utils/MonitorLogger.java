package io.github.pleuvoir.gateway.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印监控用日志用
 *
 */
public class MonitorLogger {

	private static Logger logger = LoggerFactory.getLogger("monitorLogger");

	public static Logger getLogger(){
		return logger;
	}

	/**
	 * 打印日志
	 * @param name 业务名称
	 * @param time 耗时(毫秒)
	 * @param startTime 开始时间(毫秒)
	 * @param endTime 结束时间(毫秒)
	 * @param className 类名
	 * @param methodName 方法名
	 * @param mid 商户号（如果有）
	 * @param orderNo 订单号（如果有）
	 * @param expand 扩展字段
	 */
	public static void print(String name, long time, long startTime, long endTime, String className, String methodName, String mid, String orderNo, String expand){
		logger.info("{}|{}|{}|{}|{}|{}|{}|{}|{}", name, time, startTime, endTime, className, methodName, mid, orderNo, expand);
	}
}
