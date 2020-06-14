package io.github.pleuvoir.redpack.model.dto;

import com.google.common.base.Objects;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 异步持久化
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
public class RedpackPersistDTO implements Serializable {

    private Long id;

    private Long activityId;

    private BigDecimal amount;

    private Integer status;

    private Integer version;

    private Long userId;

    private LocalDateTime createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedpackPersistDTO that = (RedpackPersistDTO) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(activityId, that.activityId) &&
                Objects.equal(amount, that.amount) &&
                Objects.equal(status, that.status) &&
                Objects.equal(version, that.version) &&
                Objects.equal(userId, that.userId) &&
                Objects.equal(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, activityId, amount, status, version, userId, createTime);
    }
}
