package com.gogoyang.lifecapsule.meta.task.service;

import com.gogoyang.lifecapsule.meta.task.entity.Task;

import java.util.ArrayList;
import java.util.Map;

public interface ITaskService {
    /**
     * 创建一个新任务
     * @param task
     */
    void createTask(Task task) throws Exception;

    /**
     * 读取任务列表
     * @param qIn
     * createUserId
     * @return
     */
    ArrayList<Task> listTask(Map qIn) throws Exception;

    Task getTask(Map qIn) throws Exception;

    /**
     * 修改任务
     * @param qIn
     * status
     * endTime
     * taskId
     */
    void updateTask(Map qIn) throws Exception;
}
