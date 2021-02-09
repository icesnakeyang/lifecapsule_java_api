package com.gogoyang.lifecapsule.controller.task;

import lombok.Data;

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
}
