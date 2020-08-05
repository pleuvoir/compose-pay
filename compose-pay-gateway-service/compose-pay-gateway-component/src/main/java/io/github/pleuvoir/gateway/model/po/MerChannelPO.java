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
import lombok.Data;

/**
 * 商户通道关系
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer_channel")
public class MerChannelPO {

    /**
     * 可以支付
     */
    public static final String CAN_PAY = "1";

    /**
     * 不可支付
     */
    public static final String NO_PAY = "0";

    @TableId("id")
    private String id;

    @TableField("mid")
    private String mid;             //商户号

    @TableField("channel_code")
    private Integer channelCode;        //通道编号

    @TableField("pay_type")
    private String payType;        //支付种类

    @TableField("pay_way")
    private String payWay;        //支付方式

    @TableField("channel_mid")
    private String channelMid;        //上游商户号

    @TableField("encrypt_key")
    private String encryptKey;        //秘钥

    @TableField("extend_params")
    private String extendParams;        //扩展参数

    @TableField("is_pay")
    private String isPay;        //是否可支付	0不可支付  1可以支付

    @TableField("private_key")
    private String privateKey;        //私钥	通道下发的商户私钥，非对称加密时有效

    @TableField("public_key")
    private String publicKey;        //公钥	通道的公钥，非对称加密时有效

    @TableField("priority")
    private Integer priority;        //优先级	 2比1大
}
