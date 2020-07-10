package io.github.pleuvoir.openapi.common.utils;

import com.alibaba.fastjson.JSON;

public interface ToJSON {

    default String toJSON() {
        return JSON.toJSONString(this);
    }
}
