package io.github.pleuvoir.redpack.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@AllArgsConstructor
@Getter
public enum RspCode {

    SUCCESS("SUCCESS", "成功"),

    ERROR("ERROR", "系统忙，请稍后重试"),

    FAIL("FAIL", "操作失败"),

    NOT_LOGIN("NOT_LOGIN", "用户未登录"),

    INVALID_ARGUMENTS("INVALID_ARGUMENTS", "参数错误"),

    REDPACK_NOT_EXIST("REDPACK_NOT_EXIST", "红包不存在"),

    ;

    private String code;
    private String msg;

    /**
     * 若参数是字符串，判断字符串是否与code相同<br>
     * 若参数是RspCode对象，判断其中的code是否相同
     */
    public boolean isEquals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return this.getCode().equals((String) obj);
        } else if (obj instanceof RspCode) {
            return this.getCode().equals(((RspCode) obj).getCode());
        }
        return false;
    }
}
