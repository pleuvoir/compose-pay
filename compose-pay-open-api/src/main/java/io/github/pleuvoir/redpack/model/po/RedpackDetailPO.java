package io.github.pleuvoir.redpack.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 红包发放明细
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_redpack_detail")
public class RedpackDetailPO {

    @TableId("id")
    private Long id;

    /**
     * 抢到的金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;


    /**
     * 红包编号
     */
    @TableField("redpack_id")
    private Long redpackId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
