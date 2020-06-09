package io.github.pleuvoir.channel.extension;

import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.TransEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的渠道服务插件实现
 *
 * <p>
 * 保存渠道、交易类型，实现类的关系
 * <p>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class DefaultChannelServicePlugin implements ChannelServicePlugin {

    private static final String ELEM_CHANNEL = "channel";
    private static final String ELEM_SERVICE = "service";
    private static final String ELEM_BEAN = "bean";

    private static final String ATTR_ID = "id";
    private static final String ATTR_CLASS = "class";

    private String location;

    //交易服务类：通道、交易类型，实现类
    private Table<ChannelEnum, TransEnum, Class<?>> services = Tables.newCustomTable(new ConcurrentHashMap<ChannelEnum, Map<TransEnum, Class<?>>>(), new Supplier<Map<TransEnum, Class<?>>>() {
        @Override
        public Map<TransEnum, Class<?>> get() {
            return new ConcurrentHashMap<TransEnum, Class<?>>();
        }
    });


    @Override
    public void load() throws Exception {
        log.info("初始化服务插件，location={}", this.location);
        Resource[] allRes = scanning();
        for (Resource res : allRes) {
            InputStream in = res.getInputStream();
            try {
                log.info("加载服务实现类：{}", res.getFile().getAbsolutePath());
                SAXReader reader = new SAXReader();
                Document doc = reader.read(in);
                Element root = doc.getRootElement();
                if (root == null)
                    continue;

                ChannelEnum channel = parseChannel(root);
                putInto(channel, root);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void putInto(ChannelEnum channel, Element rootElem) throws ClassNotFoundException {
        Element transElem = rootElem.element(ELEM_SERVICE);
        if (transElem == null)
            return;
        Iterator<Element> it = transElem.elementIterator(ELEM_BEAN);
        while (it.hasNext()) {
            Element beanElem = it.next();
            String id = beanElem.attributeValue(ATTR_ID);
            String className = beanElem.attributeValue(ATTR_CLASS);
            services.put(channel, TransEnum.toEnum(id), Class.forName(className));
        }
    }

    private ChannelEnum parseChannel(Element rootElem) {
        String channel = rootElem.elementTextTrim(ELEM_CHANNEL);
        return ChannelEnum.toEnum(channel);
    }

    private Resource[] scanning() {
        try {
            return ResourcePatternUtils
                    .getResourcePatternResolver(new PathMatchingResourcePatternResolver())
                    .getResources(location);
        } catch (IOException e) {
            log.error("加载服务实现类失败: " + location, e);
        }
        return null;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取交易类型和服务类
     *
     * @param channel 通道
     */
    @Override
    public Map<TransEnum, Class<?>> getTransMap(ChannelEnum channel) {
        return services.row(channel);
    }

    /**
     * 获取服务类名
     *
     * @param channel 通道
     * @param trans   交易类型
     */
    @Override
    public Class<?> getServiceName(ChannelEnum channel, TransEnum trans) {
        return services.get(channel, trans);
    }

    @Override
    public Set<ChannelEnum> getChannels() {
        return services.rowKeySet();
    }

}
