package io.github.pleuvoir.channel.plugin;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.channel.common.ChannelEnum;
import io.github.pleuvoir.channel.common.SceneEnum;
import io.github.pleuvoir.channel.common.ServiceIdEnum;
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
 * 默认的通道服务插件实现
 *
 * <p>
 * 保存通道、支付场景，实现类的关系
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

    //交服务类：通道、服务类别，实现类
    private Table<ChannelEnum, ServiceIdEnum, Class<?>> services = Tables.newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);


    @Override
    public void load() throws Exception {
        log.info("初始化服务插件，location={}", this.location);
        Resource[] resources = scanning();
        for (Resource res : resources) {
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
            services.put(channel, ServiceIdEnum.toEnum(id), Class.forName(className));
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

    @Override
    public Map<ServiceIdEnum, Class<?>> getServiceMap(ChannelEnum channel) {
        return services.row(channel);
    }

    @Override
    public Class<?> getServiceName(ChannelEnum channel, ServiceIdEnum serviceId) {
        return services.get(channel, serviceId);
    }

    @Override
    public Set<ChannelEnum> getChannels() {
        return services.rowKeySet();
    }

}
