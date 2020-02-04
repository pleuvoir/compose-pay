package io.github.pleuvoir.channel.core;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface Plugin {

    /**
     * 加载插件
     */
    void load() throws Exception;

    /**
     * 设置插件位置
     */
    void setLocation(String location);
}
