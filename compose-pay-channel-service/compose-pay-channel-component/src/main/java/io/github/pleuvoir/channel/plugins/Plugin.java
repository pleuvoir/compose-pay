package io.github.pleuvoir.channel.plugins;

/**
 * 插件
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
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
