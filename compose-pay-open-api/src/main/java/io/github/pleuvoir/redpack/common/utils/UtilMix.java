package io.github.pleuvoir.redpack.common.utils;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class UtilMix {

    /**
     * 获取金额
     */
    public static BigDecimal getRandomRedPacketBetweenMinAndMax(BigDecimal min, BigDecimal max) {
        float minF = min.floatValue();
        float maxF = max.floatValue();
        //生成随机数
        BigDecimal db = new BigDecimal(Math.random() * (maxF - minF) + minF);
        //返回保留两位小数的随机数。不进行四舍五入
        return db.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal getRedpackAmount(BigDecimal amount) {
        return getRandomRedPacketBetweenMinAndMax(new BigDecimal("0.01"), amount);
    }
}
