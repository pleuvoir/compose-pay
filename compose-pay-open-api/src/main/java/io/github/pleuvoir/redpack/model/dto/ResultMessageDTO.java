package io.github.pleuvoir.redpack.model.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.pleuvoir.redpack.common.RspCode;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回值
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@Accessors(chain = true)
public class ResultMessageDTO<T> {

    private static final long serialVersionUID = 7781316982699830573L;

    private String code;//返回码

    private String msg;//返回信息

    private T data;     //数据

    public ResultMessageDTO() {
    }

    public ResultMessageDTO(RspCode rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
    }

    public ResultMessageDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMessageDTO(RspCode rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
    }

    public ResultMessageDTO<T> setResult(RspCode rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
        return this;
    }

    public ResultMessageDTO<T> setResult(RspCode rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSuccess() {
        return RspCode.SUCCESS.isEquals(code);
    }

    public static <T> ResultMessageDTO<T> success() {
        return new ResultMessageDTO<>(RspCode.SUCCESS, RspCode.SUCCESS.getMsg());
    }

    public static <T> ResultMessageDTO<T> success(T data) {
        ResultMessageDTO<T> messageVO = new ResultMessageDTO<>(RspCode.SUCCESS, RspCode.SUCCESS.getMsg());
        messageVO.setData(data);
        return messageVO;
    }

    public static <T> ResultMessageDTO<T> fail(String message) {
        return new ResultMessageDTO<>(RspCode.FAIL, message);
    }

    public static <T> ResultMessageDTO<T> fail(RspCode rspCode, String message) {
        return new ResultMessageDTO<>(rspCode, message);
    }

    public static <T> ResultMessageDTO<T> fail(RspCode rspCode) {
        return new ResultMessageDTO<>(rspCode, rspCode.getMsg());
    }

    public ResultMessageDTO<T> setFail(String message) {
        return this.setResult(RspCode.FAIL, message);
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }
}
