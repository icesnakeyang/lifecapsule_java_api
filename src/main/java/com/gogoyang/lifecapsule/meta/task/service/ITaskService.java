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
     * noteId
     * taskType
     * offset
     * size
     * @return
     */
    ArrayList<Task> listTask(Map qIn) throws Exception;

    /**
     * 统计任务总数
     * @param qIn
     * @return
     */
    Integer totalTask(Map qIn);

    Task getTask(Map qIn) throws Exception;

    /**
     * 修改任务
     * @param qIn
     * taskId
     * taskTitle
     * priority
     * status
     * taskType
     * important
     * complete
     * endTime
     * userEncodeKey
     */
    void updateTask(Map qIn) throws Exception;

    /**
     * 删除任务
     * @param qIn
     * noteId
     * taskId
     */
    void deleteTask(Map qIn) throws Exception;
}
