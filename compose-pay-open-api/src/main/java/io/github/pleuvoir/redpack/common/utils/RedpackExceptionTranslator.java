package io.github.pleuvoir.redpack.common.utils;

import org.springframework.util.Assert;

import io.github.pleuvoir.redpack.common.RspCode;
import io.github.pleuvoir.redpack.exception.LockInterruptedException;
import io.github.pleuvoir.redpack.exception.RedpackException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RedpackExceptionTranslator {

	private RedpackExceptionTranslator() {
		super();
	}

	public static RedpackException convertRedpackException(Exception e) {
		Assert.notNull(e, "Exception must not be null");
		if (e instanceof RedpackException) {
			return (RedpackException) e;
		}
		if (e instanceof LockInterruptedException) {
			return new RedpackException(RspCode.ERROR);
		}
		log.error("系统错误，未知的异常：", e);
		return new RedpackException(RspCode.ERROR);
	}

}
