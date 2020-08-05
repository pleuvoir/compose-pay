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
package io.github.pleuvoir.utils;

import io.github.pleuvoir.channel.model.request.PaymentDTO;
import io.github.pleuvoir.gateway.common.utils.HibernateValidatorUtils;
import io.github.pleuvoir.gateway.common.utils.ValidationResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.junit.Test;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class HibernateValidatorUtilsTest {

    PaymentDTO dto = new PaymentDTO();

    @Test
    public void validateEntityTest() {
        ValidationResult validationResult = HibernateValidatorUtils.validateEntity(dto);
        log.info("{}，{}", validationResult.isHasErrors(), validationResult.getErrorMsg());

        ValidationResult validationResult2 = HibernateValidatorUtils.validateEntity(dto);
        log.info("{}，{}", validationResult2.isHasErrors(), validationResult2.getErrorMsg());
        log.info("{}",validationResult2.getErrorMessageOneway());
}


    @Test
    public void validateGroupTest() {
        Model model = new Model();
        ValidationResult validationResult = HibernateValidatorUtils.validateEntity(model, Model.UpdateOrderGroup.class);
        log.info("{}，{}", validationResult.isHasErrors(), validationResult.getErrorMsg());
    }

    /**
     * 嵌套验证
     */
    @Test
    public void validateNestTest() {
        Model model = new Model();

        NestedModel nestedModel = new NestedModel();
        model.setNestedModel(nestedModel);
        ValidationResult validationResult = HibernateValidatorUtils.validateEntity(model);
        log.info("{}，{}", validationResult.isHasErrors(), validationResult.getErrorMsg());
    }


    @Data
    public static class Model {

        @Length(max = 15)
        @NotEmpty
        private String midModel;            //商户号

        @Length(max = 32)
        @NotEmpty
        private String orderNoModel;        //商户系统中的订单号

        @Valid  //必须添加
        @NotNull
        private NestedModel nestedModel;

        @NotBlank(groups = UpdateOrderGroup.class, message = "平台流水号不能为空")
        private String serialNoModel;                    // 流水号


        public @interface UpdateOrderGroup {
        }
    }

    @Data
    public static class NestedModel {

        @Length(max = 15)
        @NotEmpty
        private String midNestedModel;            //商户号

        @Length(max = 32)
        @NotEmpty
        private String orderNoNestedModel;        //商户系统中的订单号

    }

}
