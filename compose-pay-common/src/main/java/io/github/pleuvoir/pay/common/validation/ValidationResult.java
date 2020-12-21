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
package io.github.pleuvoir.pay.common.validation;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class ValidationResult implements Serializable {

    private static final long serialVersionUID = 4062728936709602880L;

    //校验结果是否有错
    private boolean hasErrors = false;

    //校验错误信息
    private Map<String, String> errorMsg;

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        String errorMessage = errorMsg == null ? StringUtils.EMPTY : errorMsg.toString();
        return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg="
                + errorMessage + "]";
    }

    /**
     * 获取一条错误消息
     */
    public String getErrorMessageOneway() {
        if (this.errorMsg == null || this.errorMsg.isEmpty()) {
            return StringUtils.EMPTY;
        }
        Entry<String, String> entry = this.errorMsg.entrySet().iterator().next();
        return entry.getValue();
    }
}
