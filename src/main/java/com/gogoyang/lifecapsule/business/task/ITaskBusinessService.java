package com.gogoyang.lifecapsule.business.task;

import java.util.Map;

public interface ITaskBusinessService {
    void createTask(Map in) throws Exception;

    Map listTask(Map in) throws Exception;

    void completeTask(Map in) throws Exception;

    Map getTask(Map in) throws Exception;

    void deleteTask(Map in) throws Exception;
}
