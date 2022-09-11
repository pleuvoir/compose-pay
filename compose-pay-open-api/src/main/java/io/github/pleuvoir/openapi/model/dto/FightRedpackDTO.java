package io.github.pleuvoir.openapi.model.dto;

import lombok.Data;

/**
 * 抢红包
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
public class FightRedpackDTO {


    /**
     * 用户编号
     */
    private Long userId;


    /**
     * 活动编号
     */
    private Long activityId;
}
