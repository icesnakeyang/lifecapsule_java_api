package com.gogoyang.lifecapsule.meta.condition.entity;

import lombok.Data;

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

    /**
     * 触发条件名称
     */
    private String name;

    /**
     * 触发条件
     */
    private String gogoKey;

    /**
     * 触发条件值
     */
    private Object params;

    /**
     * 备注
     */
    private String remark;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
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

    public String getGogoKey() {
        return gogoKey;
    }

    public void setGogoKey(String gogoKey) {
        this.gogoKey = gogoKey;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
