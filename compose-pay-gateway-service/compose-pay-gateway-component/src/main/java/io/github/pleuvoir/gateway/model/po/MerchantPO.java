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
package io.github.pleuvoir.gateway.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.github.pleuvoir.gateway.common.utils.ToJSON;
import lombok.Data;

/**
 * 商户信息
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer")
public class MerchantPO implements ToJSON {

    /**
     * 商户状态-冻结
     */
    public static final Integer STATUS_FREEZE = 0;

    /**
     * 商户状态-正常
     */
    public static final Integer STATUS_NORMAL = 1;

    @TableId("id")
    private String id;

    @TableField("mid")
    private String mid;

    @TableField("mer_name")
    private String merName;        //商户名称

    @TableField("encrypt_key")
    private String encryptKey;        //加密签名密钥

    @TableField("status")
    private Integer status;        //状态
}
