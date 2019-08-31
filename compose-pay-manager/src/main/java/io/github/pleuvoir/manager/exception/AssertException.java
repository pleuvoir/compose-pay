package io.github.pleuvoir.manager.exception;

import io.github.pleuvoir.manager.common.util.AssertUtil;

/**
 * 当使用{@link AssertUtil}进行判断时，判断失败时抛出<br>
 * 该异常是一个{@link BusinessException}异常
 * @author abeir
 *
 */
public class AssertException extends BusinessException {

	private static final long serialVersionUID = -2621998046679270326L;

	public AssertException() {
		super();
	}

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException(Throwable cause) {
		super(cause);
	}

}
