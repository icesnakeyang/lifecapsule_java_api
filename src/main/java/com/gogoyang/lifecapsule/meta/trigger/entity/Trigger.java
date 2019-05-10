package com.gogoyang.lifecapsule.meta.trigger.entity;

import lombok.Data;

import java.util.Date;

/**
 * 触发器
 * 触发器定义了一系列的触发条件和接收人
 * 当所有条件满足，即把笔记发送给所有接收人
 */
@Data
public class Trigger {
    /**
     * 自增主键
     */
    private Integer ids;

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 笔记id
     */
    private String noteId;

    /**
     * 触发器名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 说明
     */
    private String remark;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
