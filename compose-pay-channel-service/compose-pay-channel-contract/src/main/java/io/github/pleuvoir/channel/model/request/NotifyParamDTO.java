package io.github.pleuvoir.channel.model.request;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收通道的通知参数<br>
 * 包含header、body以及查询参数
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
public class NotifyParamDTO {


    //http请求的header
    private Map<String, String> header = new HashMap<>();

    //http请求的body
    private String body;

    //http请求的查询参数
    private Map<String, String> param = new HashMap<>();

    /**
     * 添加header参数
     */
    public void putHeader(String key, String value) {
        header.put(key, value);
    }

    /**
     * 添加查询参数
     */
    public void putParam(String key, String value) {
        param.put(key, value);
    }
}
