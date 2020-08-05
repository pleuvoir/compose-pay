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

import java.math.BigDecimal;

/**
 * 支付流水表
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer_pay")
public class MerPayPO implements ToJSON {

    /**
     * 支付状态 - 待支付
     */
    public static final Integer PAY_STATUS_WAIT = 1;

    /**
     * 支付状态 - 已支付
     */
    public static final Integer PAY_STATUS_PAID = 2;

    /**
     * 退款状态 - 初始
     */
    public static final Integer REFUND_STATUS_INIT = 1;


    @TableId("id")
    private Long id;

    @TableField("serial_no")
    private Long serialNo;  //平台流水号

    @TableField("order_no")
    private String orderNo; //商户订单号

    @TableField("trans_unique_id")
    private Long transUniqueId;  //交易唯一ID，支持同一笔业务订单发起多次支付请求

    @TableField("channel_serial_no")
    private Long channelSerialNo;  //通道流水号

    @TableField("mid")
    private Long mid;  //商户号

    @TableField("channel_code")
    private Integer channelCode; //通道编号

    @TableField("pay_type")
    private String payType; //支付种类

    @TableField("pay_way")
    private String payWay; //支付方式

    @TableField("pay_scene")
    private String payScene; //支付产品

    @TableField("channel_mid")
    private String channelMid; //通道商户号

    @TableField("channel_fee")
    private BigDecimal channelFee; //通道手续费

    @TableField("channle_fee_real")
    private BigDecimal channelFeeReal; //通道精准手续费

    @TableField("total_amount")
    private BigDecimal totalAmount; //发生金额

    @TableField("actual_amount")
    private String actualAmount; //用户实际支付金额

    @TableField("subject")
    private String subject; //标题

    @TableField("body")
    private String body; //描述

    @TableField("pay_status")
    private Integer payStatus; //支付状态

    @TableField("refund_status")
    private Integer refundStatus; //退款状态

}
