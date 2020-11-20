package com.gogoyang.lifecapsule.meta.task.service;

import com.gogoyang.lifecapsule.meta.task.dao.TaskDao;
import com.gogoyang.lifecapsule.meta.task.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TaskService implements ITaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void createTask(Task task) throws Exception {
        taskDao.createTask(task);
    }

    @Override
    public ArrayList<Task> listTask(Map qIn) throws Exception {
        ArrayList<Task> tasks = taskDao.listTask(qIn);
        return tasks;
    }

    @Override
    public Task getTask(Map qIn) throws Exception {
        Task task = taskDao.getTask(qIn);
        return task;
    }

    /**
     * 修改任务
     * @param qIn
     * status
     * endTime
     * taskId
     */
    @Override
    public void updateTask(Map qIn) throws Exception {
        taskDao.updateTask(qIn);
    }
}
