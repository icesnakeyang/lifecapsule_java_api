package com.gogoyang.lifecapsule.meta.condition.entity;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
import lombok.Data;

import java.util.List;

/**
 * 触发条件
 * MongoDB集合
 */
@Data
public class Condition {
    /**
     * 唯一id
     */
    private Integer _id;

    /**
     * 触发条件Id
     */
    private String conditionId;

    /**
     * 触发器Id
     */
    private String triggerId;

    private String uuid;

    private String type;

    /**
     * 触发条件值
     */
    private List<KeyParams> params;
}
