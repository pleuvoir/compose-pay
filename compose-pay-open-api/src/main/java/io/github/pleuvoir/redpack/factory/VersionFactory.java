package io.github.pleuvoir.redpack.factory;

import io.github.pleuvoir.redpack.service.IRedpackService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Component
public class VersionFactory {

    @Resource(name = "redpackServiceImplV1")
    private IRedpackService serviceV1;
    @Resource(name = "redpackServiceImplV2")
    private IRedpackService serviceV2;
    @Resource(name = "redpackServiceImplV3")
    private IRedpackService serviceV3;
    @Resource(name = "redpackServiceImplV4")
    private IRedpackService serviceV4;
    @Resource(name = "redpackServiceImplV5")
    private IRedpackService serviceV5;
    @Resource(name = "redpackServiceImplV6")
    private IRedpackService serviceV6;

    public IRedpackService route(String version) {
        switch (version) {
            case "v1":
                return serviceV1;
            case "v2":
                return serviceV2;
            case "v3":
                return serviceV3;
            case "v4":
                return serviceV4;
            case "v5":
                return serviceV5;
            case "v6":
                return serviceV6;
        }
        return null;
    }

}
