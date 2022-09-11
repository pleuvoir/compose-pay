/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.gateway.utils;


import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import io.github.pleuvoir.pay.common.exception.BusinessException;

/**
 * 断言工具，判断失败时抛出{@link BusinessException}
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
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
