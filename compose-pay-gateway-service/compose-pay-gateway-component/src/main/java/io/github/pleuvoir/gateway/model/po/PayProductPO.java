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
