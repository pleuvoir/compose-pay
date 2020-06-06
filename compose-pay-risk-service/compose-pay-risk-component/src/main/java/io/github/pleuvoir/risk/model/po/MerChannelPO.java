package io.github.pleuvoir.gateway.model.po;

/**
 * 商户通道关系
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
    private String channelCode;        //通道编号

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
    private Integer priority;		//优先级	 2比1大
}
