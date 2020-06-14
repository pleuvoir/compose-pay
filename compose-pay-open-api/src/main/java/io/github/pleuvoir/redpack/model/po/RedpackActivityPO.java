package io.github.pleuvoir.redpack.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 红包活动
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_redpack_activity")
public class RedpackActivityPO {

    @TableId("id")
    private Long id;

    /**
     * 红包总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 剩余金额
     */
    @TableField("surplus_amount")
    private BigDecimal surplusAmount;

    /**
     * 红包总数
     */
    @TableField("total")
    private Integer total;

    /**
     * 红包剩余总数
     */
    @TableField("surplus_total")
    private Integer surplusTotal;

    /**
     * 版本号
     */
    @Version
    @TableField("version")
    private Integer version;

}
