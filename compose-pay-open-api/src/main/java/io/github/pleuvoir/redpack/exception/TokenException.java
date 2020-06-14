package io.github.pleuvoir.redpack.exception;

import io.github.pleuvoir.redpack.common.RspCode;

/**
 * Token异常
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class TokenException extends Exception {

    private static final long serialVersionUID = -19751552019997786L;

    private RspCode RspCode;
    private String msg;

    public TokenException() {
        super();
    }

    public TokenException(RspCode rsCode, String message) {
        super(rsCode.getCode() + ":" + message);
        this.RspCode = rsCode;
        this.msg = message;
    }

    public TokenException(RspCode rsCode, Throwable cause) {
        super(rsCode.getCode() + ":" + rsCode.getMsg(), cause);
        this.RspCode = rsCode;
        this.msg = rsCode.getMsg();
    }

    public TokenException(RspCode rsCode, String message, Throwable cause) {
        super(rsCode.getCode() + ":" + message, cause);
        this.RspCode = rsCode;
        this.msg = message;
    }

    public RspCode getRspCode() {
        return RspCode;
    }

    public String getMsg() {
        return msg;
    }
}
