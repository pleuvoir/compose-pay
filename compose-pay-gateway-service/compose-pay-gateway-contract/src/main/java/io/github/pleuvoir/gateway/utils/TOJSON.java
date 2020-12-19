package io.github.pleuvoir.gateway.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface TOJSON {

    default String toJSON() {
        return JSON.toJSONString(this);
    }
}
