package io.github.pleuvoir.gateway.model.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回消息
 *
 * @author pleuvoir
 */
@Data
@Accessors(chain = true)
public class ResultMessageVO<T> implements Serializable {

    private static final long serialVersionUID = 7781316982699830573L;

    private String code;//返回码

    private String msg;//返回信息

    private T data;     //数据

    public ResultMessageVO() {
    }

    public ResultMessageVO(RspCodeEnum rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
    }

    public ResultMessageVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMessageVO(RspCodeEnum rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
    }

    public ResultMessageVO<T> setResult(RspCodeEnum rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
        return this;
    }

    public ResultMessageVO<T> setResult(RspCodeEnum rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSuccess() {
        return RspCodeEnum.SUCCESS.isEquals(code);
    }

    public static <T> ResultMessageVO<T> success() {
        return new ResultMessageVO<>(RspCodeEnum.SUCCESS, RspCodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResultMessageVO<T> fail(String message) {
        return new ResultMessageVO<>(RspCodeEnum.FAIL, message);
    }

    public static <T> ResultMessageVO<T> fail(RspCodeEnum rspCode, String message) {
        return new ResultMessageVO<>(rspCode, message);
    }


    public ResultMessageVO<T> setFail(String message) {
        return this.setResult(RspCodeEnum.FAIL, message);
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }

}
