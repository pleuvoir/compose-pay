package io.github.pleuvoir.gateway.utils;


import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.exception.BusinessException;

/**
 * 断言工具，判断失败时抛出{@link BusinessException}
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class AssertUtil {


    /**
     * 判断执行结果数量是否为1，为空时也认为非1
     *
     * @param rs       结果数量
     * @param errorMsg 错误信息
     */
    public static void assertOne(Integer rs, String errorMsg) throws BusinessException {
        if (rs == null || rs != 1) {
            throw new BusinessException(ResultCodeEnum.ERROR, errorMsg);
        }
    }

    /**
     * 判断执行结果数量是否为1，为空时也认为非1
     *
     * @param rs       结果数量
     * @param errorMsg 错误信息
     */
    public static void assertOne(int rs, String errorMsg) throws BusinessException {
        if (rs != 1) {
            throw new BusinessException(ResultCodeEnum.ERROR, errorMsg);
        }
    }


}
