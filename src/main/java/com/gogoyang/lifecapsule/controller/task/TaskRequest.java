package com.gogoyang.lifecapsule.controller.task;

import lombok.Data;

@Data
public class TaskRequest {
    private String content;
    private Boolean important;
    private Boolean urgent;
    private String createUserId;
    private String taskId;
}
