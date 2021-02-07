package com.gogoyang.lifecapsule.business.task;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.task.entity.Task;
import com.gogoyang.lifecapsule.meta.task.service.ITaskService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.utility.GogoTools;
import com.gogoyang.lifecapsule.utility.constant.GogoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskBusinessService implements ITaskBusinessService {
    private final ITaskService iTaskService;
    private final ICommonService iCommonService;

    public TaskBusinessService(ITaskService iTaskService,
                               ICommonService iCommonService) {
        this.iTaskService = iTaskService;
        this.iCommonService = iCommonService;
    }

    @Override
    public void createTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String content = in.get("content").toString();
        String important = (String) in.get("important");
        Boolean urgent = (Boolean) in.get("urgent");

        UserInfo userInfo = iCommonService.getUserByToken(token);

        Task task = new Task();
        task.setCreateTime(new Date());
        task.setCreateUserId(userInfo.getUserId());
        task.setImportant(important);
        task.setTaskId(GogoTools.UUID().toString());
        task.setPriority(0);
        task.setStatus(GogoStatus.PROGRESS.toString());
        task.setTaskType(GogoStatus.DEFAULT.toString());

        iTaskService.createTask(task);
    }

    @Override
    public Map listTask(Map in) throws Exception {
        String token = in.get("token").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);

        Map qIn = new HashMap();
        qIn.put("createUserId", userInfo.getUserId());
        ArrayList<Task> tasks = iTaskService.listTask(qIn);

        Map out = new HashMap();
        out.put("tasks", tasks);

        return out;
    }

    @Override
    public void completeTask(Map in) throws Exception {
        String token=in.get("token").toString();
        String taskId=in.get("taskId").toString();

        UserInfo userInfo=iCommonService.getUserByToken(token);

        Task task=iCommonService.getTaskByTaskId(taskId);

        if(!task.getCreateUserId().equals(userInfo.getUserId())){
            throw new Exception("10032");
        }

        Map qIn=new HashMap();
        qIn.put("taskId", task.getTaskId());
        qIn.put("status", GogoStatus.COMPLETE);
        qIn.put("endTime", new Date());

        iTaskService.updateTask(qIn);
    }
}
