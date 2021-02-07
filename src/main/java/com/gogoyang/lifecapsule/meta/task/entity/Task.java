package com.gogoyang.lifecapsule.meta.task.entity;

import lombok.Data;

import java.util.Date;

/**
 * 待办任务
 */
@Data
public class Task {
    private Integer ids;
    private String taskId;
    private String createUserId;
    private String taskTitle;
    private Date createTime;
    /**
     * 任务优先级
     */
    private Integer priority;
    private String status;
    private String taskType;
    /**
     * 重要性
     */
    private String important;
    /**
     * 父任务
     */
    private String pid;
    private Date endTime;
    private String noteId;
    private Boolean complete;
}
