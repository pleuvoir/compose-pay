package io.github.pleuvoir.gateway.constants;

import lombok.Getter;

/**
 * 支付方式
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum PayWayEnum {

    /**
     * 扫码支付
     */
    SCAN_CODE("01"),

    ;

    @Getter
    private String code;    //编号，数据库中存储的值


    PayWayEnum(String code) {
        this.code = code;
    }
}
