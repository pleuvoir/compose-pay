package io.github.pleuvoir.gateway.model.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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

    public ResultMessageVO(ResultCodeEnum rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
    }

    public ResultMessageVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMessageVO(ResultCodeEnum rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
    }

    public ResultMessageVO<T> setResult(ResultCodeEnum rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
        return this;
    }

    public ResultMessageVO<T> setResult(ResultCodeEnum rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.isEquals(code);
    }

    public static <T> ResultMessageVO<T> success() {
        return new ResultMessageVO<>(ResultCodeEnum.SUCCESS, ResultCodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResultMessageVO<T> success(T data) {
        ResultMessageVO<T> messageVO = new ResultMessageVO<>(ResultCodeEnum.SUCCESS, ResultCodeEnum.SUCCESS.getMsg());
        messageVO.setData(data);
        return messageVO;
    }

    public static <T> ResultMessageVO<T> fail(String message) {
        return new ResultMessageVO<>(ResultCodeEnum.FAIL, message);
    }

    public static <T> ResultMessageVO<T> fail(ResultCodeEnum rspCode, String message) {
        return new ResultMessageVO<>(rspCode, message);
    }

    public static <T> ResultMessageVO<T> fail(ResultCodeEnum rspCode) {
        return new ResultMessageVO<>(rspCode, rspCode.getMsg());
    }

    public ResultMessageVO<T> setFail(String message) {
        return this.setResult(ResultCodeEnum.FAIL, message);
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }

}
