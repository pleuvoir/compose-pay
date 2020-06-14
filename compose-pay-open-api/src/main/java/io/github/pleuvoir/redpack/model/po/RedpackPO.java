package io.github.pleuvoir.redpack.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 生成的红包
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_redpack")
public class RedpackPO implements Serializable {


    public static final int ENABLE = 1;

    public static final int DISABLE = 2;

    @TableId("id")
    private Long id;


    @TableId("activity_id")
    private Long activityId;

    /**
     * 剩余金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 红包状态 1 可用 2 不可用
     */
    @TableField("status")
    private Integer status;

    /**
     * 版本号
     */
    @Version
    @TableField("version")
    private Integer version;

}
