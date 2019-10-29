package io.github.pleuvoir.gateway.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户签约支付产品手续费表
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer_sign_fee")
public class MerSignFeePO {

    /**
     * 手续费计算方式-四舍五入
     */
    public static final String FEE_ALGORITHM_HALF = "0";

    /**
     * 手续费计算方式-向上取整
     */
    public static final String FEE_ALGORITHM_UP = "1";

    /**
     * 退还手续费-退还
     */
    public static final String REFUND_FEE_FLAG_TRUE = "0";

    /**
     * 退还手续费-不退还
     */
    public static final String REFUND_FEE_FLAG_FALSE = "1";

    @TableId("id")
    private String id;

    @TableField("mid")
    private String mid;                                //商户号

    @TableField("pay_type")
    private String payType;                            //支付种类

    @TableField("pay_way")
    private String payWay;                            //支付方式

    @TableField("pay_product")
    private String payProduct;                            //支付产品

    @TableField("rate")
    private BigDecimal rate;                            // 手续费费率

    @TableField("must_amt")
    private BigDecimal mustAmt;                            // 固定金额

    @TableField("hight_amt")
    private BigDecimal hightAmt;                            // 封顶手续费

    @TableField("low_amt")
    private BigDecimal lowAmt;                            // 保底手续费

    @TableField("trans_hight_amt")
    private BigDecimal transHightAmt;                            // 交易封顶金额

    @TableField("trans_low_amt")
    private BigDecimal transLowAmt;                            // 交易保底金额

    @TableField("refund_fee_flag")
    private String refundFeeFlag;                            //退还手续费	0：是，1：否

    @TableField("fee_algorithm")
    private String feeAlgorithm;                            //手续费计算方式		0：四舍五入 	1：向上取整

    @TableField("remark")
    private String remark;                            //手续费计算方式

    @TableField("day_hight_amt")
    private BigDecimal dayHightAmt;                // 交易日限额

}
