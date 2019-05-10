package com.gogoyang.lifecapsule.meta.condition.entity;

import lombok.Data;

/**
 * 触发条件
 * 一个触发条件包含一对key/value
 * key=预定义的触发条件
 * value=触发条件的值
 * 一旦key获取的值==value指定的值。条件就被触发，返回true
 */
@Data
public class Condition {
    /**
     * 自增主键
     */
    private Integer ids;

    /**
     * 触发条件Id
     */
    private String conditionId;

    /**
     * 触发器Id
     */
    private String triggerId;

    /**
     * 触发条件名称
     */
    private String name;

    /**
     * 触发条件
     */
    private String key;

    /**
     * 触发条件值
     */
    private String value;

    /**
     *
     */
    private String remark;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
