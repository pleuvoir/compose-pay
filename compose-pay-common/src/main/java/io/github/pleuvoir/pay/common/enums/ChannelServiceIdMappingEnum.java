package io.github.pleuvoir.pay.common.enums;

import lombok.Getter;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum ChannelServiceIdMappingEnum {


    MOCK_SCAN_CODE(31, "模拟通道扫码支付", ChannelEnum.MOCK, ServiceIdEnum.SCAN_CODE),
    MOCK_APP(32, "模拟通道扫码支付", ChannelEnum.MOCK, ServiceIdEnum.APP),

    WECHAT_APP(12, "微信APP支付", ChannelEnum.WECHAT, ServiceIdEnum.APP),

    ALIPAY_APP(22, "支付宝APP支付", ChannelEnum.ALIPAY, ServiceIdEnum.APP),

    ;


    @Getter
    private Integer mappingCode;
    @Getter
    private String desc;
    @Getter
    private ChannelEnum channelEnum;
    @Getter
    private ServiceIdEnum serviceIdEnum;


    ChannelServiceIdMappingEnum(Integer mappingCode, String desc, ChannelEnum channelEnum,
            ServiceIdEnum serviceIdEnum) {
        this.mappingCode = mappingCode;
        this.desc = desc;
        this.channelEnum = channelEnum;
        this.serviceIdEnum = serviceIdEnum;
    }


    /**
     * 获取通道服务ID映射枚举
     */
    public static ChannelServiceIdMappingEnum toEnum(Integer mappingCode) {
        if (mappingCode == null) {
            return null;
        }
        for (ChannelServiceIdMappingEnum mappingEnum : ChannelServiceIdMappingEnum.values()) {
            if (mappingEnum.getMappingCode().equals(mappingCode)) {
                return mappingEnum;
            }
        }
        return null;
    }

    /**
     * 获取通道枚举
     */
    public static ChannelEnum toChannelEnum(Integer mappingCode) {
        if (mappingCode == null) {
            return null;
        }
        for (ChannelServiceIdMappingEnum mappingEnum : ChannelServiceIdMappingEnum.values()) {
            if (mappingEnum.getMappingCode().equals(mappingCode)) {
                return mappingEnum.getChannelEnum();
            }
        }
        return null;
    }
}
