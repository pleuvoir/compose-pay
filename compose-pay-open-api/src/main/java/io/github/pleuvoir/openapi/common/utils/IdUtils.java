package io.github.pleuvoir.openapi.common.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 雪花算法ID生成器
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class IdUtils {

	/** 时间部分所占长度 */
	private static final int TIME_LEN = 41;
	/** 数据中心id所占长度 */
	private static final int DATA_LEN = 5;
	/** 机器id所占长度 */
	private static final int WORK_LEN = 5;
	/** 毫秒内序列所占长度 */
	private static final int SEQ_LEN = 12;

	/** 定义起始时间 2015-01-01 00:00:00 */
	private static final long START_TIME = 1420041600000L;
	/** 上次生成ID的时间截 */
	private static long LAST_TIME_STAMP = -1L;
	/** 时间部分向左移动的位数 22 */
	private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;

	/** 自动获取数据中心id（可以手动定义 0-31之间的数） */
	private static final long DATA_ID = getDataId();
	/** 自动机器id（可以手动定义 0-31之间的数） */
	private static final long WORK_ID = getWorkId();
	/** 数据中心id最大值 31 */
	private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
	/** 机器id最大值 31 */
	private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);
	/** 随机获取数据中心id的参数 32 */
	private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
	/** 随机获取机器id的参数 32 */
	private static final int WORK_RANDOM = WORK_MAX_NUM + 1;
	/** 数据中心id左移位数 17 */
	private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;
	/** 机器id左移位数 12 */
	private static final int WORK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;

	/** 上一次的毫秒内序列值 */
	private static long LAST_SEQ = 0L;
	/** 毫秒内序列的最大值 4095 */
	private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);

	public synchronized static long nextId() {
		long now = System.currentTimeMillis();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (now < LAST_TIME_STAMP) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds", LAST_TIME_STAMP - now));
		}

		if (now == LAST_TIME_STAMP) {
			LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
			if (LAST_SEQ == 0) {
				now = nextMillis(LAST_TIME_STAMP);
			}
		} else {
			LAST_SEQ = 0;
		}

		// 上次生成ID的时间截
		LAST_TIME_STAMP = now;

		return ((now - START_TIME) << TIME_LEFT_BIT) | (DATA_ID << DATA_LEFT_BIT) | (WORK_ID << WORK_LEFT_BIT)
				| LAST_SEQ;
	}

	/**
	 * 获取下一不同毫秒的时间戳，不能与最后的时间戳一样
	 */
	public static long nextMillis(long lastMillis) {
		long now = System.currentTimeMillis();
		while (now <= lastMillis) {
			now = System.currentTimeMillis();
		}
		return now;
	}

	/**
	 * 获取字符串s的字节数组，然后将数组的元素相加，对（max+1）取余
	 */
	private static int getHostId(String s, int max) {
		byte[] bytes = s.getBytes();
		int sums = 0;
		for (int b : bytes) {
			sums += b;
		}
		return sums % (max + 1);
	}

	/**
	 * 根据 host address 取余，发生异常就获取 0到31之间的随机数
	 */
	public static int getWorkId() {
		try {
			return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
		} catch (UnknownHostException e) {
			return new Random().nextInt(WORK_RANDOM);
		}
	}

	/**
	 * 根据 host name 取余，发生异常就获取 0到31之间的随机数
	 */
	public static int getDataId() {
		try {
			return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
		} catch (UnknownHostException e) {
			return new Random().nextInt(DATA_RANDOM);
		}
	}

}
