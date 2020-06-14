package io.github.pleuvoir.redpack.exception;

import io.github.pleuvoir.redpack.common.RspCode;

/**
 * 红包异常
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class RedpackException extends Exception {


    private static final long serialVersionUID = -8354025522492260258L;

    private RspCode rspCode;
    private String msg;

    public RedpackException(RspCode rspCode) {
        super(rspCode.getCode() + ":" + rspCode.getMsg());
        this.rspCode = rspCode;
        this.msg = rspCode.getMsg();
    }


    public RedpackException(RspCode rspCode, String message) {
        super(message);
        this.rspCode = rspCode;
        this.msg = message;
    }

    public RedpackException() {
    }

    public RedpackException(String message) {
        super(message);
    }

    public RedpackException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedpackException(Throwable cause) {
        super(cause);
    }

    public RedpackException(String fmt, Object... msg) {
        super(String.format(fmt, msg));
    }

    public RspCode getRspCode() {
        return rspCode;
    }

    public String getMsg() {
        return msg;
    }
}
