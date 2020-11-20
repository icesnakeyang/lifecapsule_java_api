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
    private Date createTime;
    private String content;
    /**
     * 任务优先级
     */
    private Integer priority;
    private String status;
    private String taskType;
    /**
     * 是否重要
     */
    private Boolean important;
    /**
     * 是否紧急
     */
    private Boolean urgent;
    /**
     * 父任务
     */
    private String pid;
    private Date endTime;
}
