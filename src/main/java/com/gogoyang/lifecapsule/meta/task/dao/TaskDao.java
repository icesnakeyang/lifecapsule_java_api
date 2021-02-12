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
     * taskType
     * offset
     * size
     * @return
     */
    ArrayList<Task> listTask(Map qIn);

    /**
     * 统计任务总数
     * @param qIn
     * @return
     */
    Integer totalTask(Map qIn);

    Task getTask(Map qIn);

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
    void updateTask(Map qIn);

    /**
     * 删除任务
     * @param qIn
     * noteId
     * taskId
     */
    void deleteTask(Map qIn);
}
