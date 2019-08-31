package io.github.pleuvoir.manager.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常
 */
public class KtaAuthenticationException extends AuthenticationException {
	private static final long serialVersionUID = -121219158129626814L;

	public KtaAuthenticationException() {
		super();
	}

	public KtaAuthenticationException(String message) {
		super(message);
	}

	public KtaAuthenticationException(Throwable cause) {
		super(cause);
	}

	public KtaAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}
