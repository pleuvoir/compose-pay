/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.pay.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 7781316982699830573L;

    private Integer code;//返回码

    private String msg;//返回信息

    private T data;     //数据

    public Result() {
    }

    public Result(ResultCodeEnum rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultCodeEnum rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
    }

    public Result<T> setResult(ResultCodeEnum rspCode) {
        this.code = rspCode.getCode();
        this.msg = rspCode.getMsg();
        return this;
    }

    public Result<T> setResult(ResultCodeEnum rspCode, String msg) {
        this.code = rspCode.getCode();
        this.msg = msg;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.isEquals(code);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS, ResultCodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(ResultCodeEnum.SUCCESS, ResultCodeEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCodeEnum.FAIL, message);
    }

    public static <T> Result<T> fail(ResultCodeEnum rspCode, String message) {
        return new Result<>(rspCode, message);
    }

    public static <T> Result<T> fail(ResultCodeEnum rspCode) {
        return new Result<>(rspCode, rspCode.getMsg());
    }
}
