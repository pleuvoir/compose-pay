package io.github.pleuvoir.gateway.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 商户IP
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer_ip")
public class MerIpPO {

    @TableId("id")
    private String id;

    @TableField("mid")
    private String mid;

    @TableField("ip")
    private String ip;

    @TableField("remark")
    private String remark;
}
