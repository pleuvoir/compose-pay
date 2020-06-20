package io.github.pleuvoir.gateway.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.github.pleuvoir.gateway.common.utils.ToJSON;
import lombok.Data;

/**
 * 支付流水表
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer_pay")
public class MerPayPO implements ToJSON {

    @TableId("id")
    private Long id;

    @TableField("serial_no")
    private Long serialNo;  //平台流水号

    @TableField("order_no")
    private String orderNo; //商户订单号

    @TableField("trans_unique_id")
    private Long transUniqueId;  //交易唯一ID


}
