package io.github.pleuvoir.openapi.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回值
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
