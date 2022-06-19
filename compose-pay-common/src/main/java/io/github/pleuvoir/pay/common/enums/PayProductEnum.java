package io.github.pleuvoir.pay.common.enums;

import lombok.Getter;

/**
 * 支付产品枚举
 * <p> 调用方可以通过传递该值确定具体的支付产品
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public enum PayProductEnum {


    MOCK_H5(9999101, ChannelEnum.MOCK, "模拟通道H5支付");


    @Getter
    private Integer code;
    @Getter
    private ChannelEnum channel;
    @Getter
    private String desc;


    PayProductEnum(Integer code, ChannelEnum channel, String desc) {
        this.channel = channel;
        this.code = code;
        this.desc = desc;
    }


    /**
     * 转换为支付渠道枚举
     */
    public static PayProductEnum toPayProductEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (PayProductEnum payChannelEnum : PayProductEnum.values()) {
            if (payChannelEnum.getCode().equals(code)) {
                return payChannelEnum;
            }
        }
        return null;
    }

    public boolean isEqual(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof PayProductEnum) {
            PayProductEnum channelEnum = (PayProductEnum) o;
            return channelEnum.equals(this);
        }
        if (o instanceof Integer) {
            Integer code = (Integer) o;
            return code.equals(this.getCode());
        }
        return false;
    }

}
