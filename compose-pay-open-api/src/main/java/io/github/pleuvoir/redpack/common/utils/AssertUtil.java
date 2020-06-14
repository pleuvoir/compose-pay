package io.github.pleuvoir.redpack.common.utils;


import io.github.pleuvoir.redpack.common.RspCode;
import io.github.pleuvoir.redpack.exception.RedpackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 断言工具，判断失败时抛出{@link RedpackException}
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class AssertUtil {

    private static final Logger logger = LoggerFactory.getLogger(AssertUtil.class);

    /**
     * 判断执行结果数量是否为1，为空时也认为非1
     *
     * @param rs       结果数量
     * @param errorMsg 错误信息
     * @throws RedpackException
     */
    public static void assertOne(Integer rs, String errorMsg) throws RedpackException {
        if (rs == null || rs != 1) {
           // logger.warn(errorMsg);
            throw new RedpackException(RspCode.ERROR, errorMsg);
        }
    }

    /**
     * 判断执行结果数量是否为1，为空时也认为非1
     *
     * @param rs       结果数量
     * @param errorMsg 错误信息
     * @throws RedpackException
     */
    public static void assertOne(int rs, String errorMsg) throws RedpackException {
        if (rs != 1) {
            logger.warn(errorMsg);
            throw new RedpackException(RspCode.ERROR, errorMsg);
        }
    }

    /**
     * 判断执行结果是否是预期的数量，为空时也认为非预期
     *
     * @param rs       结果数量
     * @param expected 预期的数量
     * @param errorMsg 错误信息
     * @throws RedpackException
     */
    public static void assertMulti(Integer rs, int expected, String errorMsg) throws RedpackException {
        if (rs == null || rs != expected) {
            logger.warn(errorMsg);
            throw new RedpackException(RspCode.ERROR, errorMsg);
        }
    }

    /**
     * 判断执行结果数量是否为1，为空时也认为非1
     *
     * @param rs       结果数量
     * @param errorMsg 错误信息
     * @throws RedpackException
     */
    public static void assertOne(Integer rs, Logger inlog, String errorMsg) throws RedpackException {
        if (rs == null || rs != 1) {
            inlog.warn(errorMsg);
            throw new RedpackException(RspCode.ERROR, errorMsg);
        }
    }

    /**
     * 判断执行结果数量是否为1，为空时也认为非1
     *
     * @param rs       结果数量
     * @param errorMsg 错误信息
     * @throws RedpackException
     */
    public static void assertOne(int rs, Logger inlog, String errorMsg) throws RedpackException {
        if (rs != 1) {
            inlog.warn(errorMsg);
            throw new RedpackException(RspCode.ERROR, errorMsg);
        }
    }

}
