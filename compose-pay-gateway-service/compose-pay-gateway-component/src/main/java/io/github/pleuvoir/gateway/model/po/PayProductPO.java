/*
 * Copyright © 2020 pleuvoir (pleuvoir@foxmail.com)
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
package io.github.pleuvoir.gateway.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.github.pleuvoir.gateway.common.utils.ToJSON;
import lombok.Data;

/**
 * 支付产品
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("p_pay_product")
public class PayProductPO implements ToJSON {

    @TableId("id")
    private String id;

    @TableField("pay_type_code")
    private String payTypeCode;   //支付种类编码

    @TableField("pay_way_code")
    private String payWayCode;  //支付方式编码

    @TableField("pay_product_code")
    private String payProductCode;  //支付产品编码

    @TableField("name")
    private String name;    //支付产品名称

    @TableField("status")
    private Integer status;  //0：禁用  1：启用

    @TableField("remark")
    private String remark;  //备注

}
