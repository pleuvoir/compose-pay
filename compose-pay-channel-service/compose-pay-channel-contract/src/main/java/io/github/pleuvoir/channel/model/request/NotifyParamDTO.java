package io.github.pleuvoir.channel.model.request;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * 接收通道的通知参数
 *
 *
 * <p>包含header、body以及查询参数
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
public class NotifyParamDTO {


    //http请求的header
    private Map<String, String> header = new HashMap<>();

    //http请求的body
    private String body;

    //http请求的查询参数
    private Map<String, String> param = new HashMap<>();

    //附加数据，我们自己添加的，非通道通知参数
    private Map<String, String> attach = new HashMap<>();


    /**
     * 添加attach参数
     */
    public NotifyParamDTO putAttach(String key, String value) {
        attach.put(key, value);
        return this;
    }

    /**
     * 删除attach参数
     */
    public NotifyParamDTO removeAttach(String key) {
        attach.remove(key);
        return this;
    }

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
