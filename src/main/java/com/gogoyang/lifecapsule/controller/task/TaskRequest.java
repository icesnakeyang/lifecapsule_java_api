package com.gogoyang.lifecapsule.controller.task;

import lombok.Data;

import java.util.Date;

@Data
public class TaskRequest {
    private String content;
    private String important;
    private String taskId;
    private Integer pageIndex;
    private Integer pageSize;
    private String title;
    private String encryptKey;
    private String keyToken;
    private String taskType;
    private Integer priority;
    private String status;
    private Boolean complete;
    private Date endTime;
    private String userEncodeKey;
}
