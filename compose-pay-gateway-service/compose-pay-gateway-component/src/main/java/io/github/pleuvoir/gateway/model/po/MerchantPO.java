package io.github.pleuvoir.gateway.model.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.github.pleuvoir.gateway.common.utils.ToJSON;
import lombok.Data;

/**
 * 商户信息
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
@TableName("t_mer")
public class MerchantPO implements ToJSON {

    /**
     * 商户状态-冻结
     */
    public static final String STATUS_FREEZE = "0";

    /**
     * 商户状态-正常
     */
    public static final String STATUS_NORMAL = "1";

    @TableId("id")
    private String id;

    @TableId("mid")
    private String mid;

    @TableId("merName")
    private String merName;        //商户名称

    @TableId("merAlias")
    private String merAlias;    //商户简称

    @TableId("encryptKey")
    private String encryptKey;        //加密签名密钥

    @TableId("status")
    private String status;        //状态
}
