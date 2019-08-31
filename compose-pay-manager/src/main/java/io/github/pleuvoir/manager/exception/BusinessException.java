package io.github.pleuvoir.manager.exception;

/**
 * 用于业务传回具体错误
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = -121219158129626814L;
	
	public BusinessException(){
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	

}
