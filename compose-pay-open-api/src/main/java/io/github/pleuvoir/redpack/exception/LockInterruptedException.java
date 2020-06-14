package io.github.pleuvoir.redpack.exception;

public class LockInterruptedException extends Exception {

	private static final long serialVersionUID = 216470953894295808L;

	public LockInterruptedException() {
    }

    public LockInterruptedException( String message ) {
        super( message );
    }

    public LockInterruptedException( String message, Throwable cause ) {
        super( message, cause );
    }

    public LockInterruptedException( Throwable cause ) {
        super( cause );
    }

    public LockInterruptedException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

}