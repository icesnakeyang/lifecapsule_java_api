package com.gogoyang.lifecapsule.meta.task.dao;

import com.gogoyang.lifecapsule.meta.task.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TaskDao {
    /**
     * 创建一个新任务
     * @param task
     */
    void createTask(Task task);

    /**
     * 读取任务列表
     * @param qIn
     * createUserId
     * noteId
     * @return
     */
    ArrayList<Task> listTask(Map qIn);

    Task getTask(Map qIn);

    /**
     * 修改任务
     * @param qIn
     * status
     * endTime
     * taskId
     * taskTitle
     */
    void updateTask(Map qIn);

    /**
     * 删除任务
     * @param qIn
     * noteId
     * taskId
     */
    void deleteTask(Map qIn);
}
