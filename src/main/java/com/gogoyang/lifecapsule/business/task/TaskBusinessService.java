package com.gogoyang.lifecapsule.business.task;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.task.entity.Task;
import com.gogoyang.lifecapsule.meta.task.service.ITaskService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.utility.GogoTools;
import com.gogoyang.lifecapsule.utility.constant.GogoStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskBusinessService implements ITaskBusinessService {
    private final ITaskService iTaskService;
    private final ICommonService iCommonService;
    private final INoteService iNoteService;

    public TaskBusinessService(ITaskService iTaskService,
                               ICommonService iCommonService,
                               INoteService iNoteService) {
        this.iTaskService = iTaskService;
        this.iCommonService = iCommonService;
        this.iNoteService = iNoteService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String content = in.get("content").toString();
        String important = (String) in.get("important");
        String title = in.get("title").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        String taskType=(String)in.get("taskType");

        UserInfo userInfo = iCommonService.getUserByToken(token);

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = iCommonService.takeNoteAES(keyToken, encryptKey);

        Task task = new Task();
        task.setCreateTime(new Date());
        task.setCreateUserId(userInfo.getUserId());
        task.setImportant(important);
        task.setTaskId(GogoTools.UUID().toString());
        task.setPriority(0);
        task.setStatus(GogoStatus.PROGRESS.toString());
        task.setTaskType(GogoStatus.DEFAULT.toString());
        task.setTaskTitle(title);
        task.setUserEncodeKey(strAESKey);
        task.setTaskType(taskType);
        iTaskService.createTask(task);

        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNoteId(task.getTaskId());
        noteDetail.setContent(content);
        noteDetail.setContentId(GogoTools.UUID().toString());
        iNoteService.createNoteDetail(noteDetail);
    }

    @Override
    public Map listTask(Map in) throws Exception {
        String token = in.get("token").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");
        String taskType=(String)in.get("taskType");
        String status=(String)in.get("status");

        UserInfo userInfo = iCommonService.getUserByToken(token);

        Map qIn = new HashMap();
        qIn.put("createUserId", userInfo.getUserId());
        Integer offset = (pageIndex - 1) * pageSize;
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        qIn.put("taskType", taskType);
        qIn.put("status", status);
        ArrayList<Task> tasks = iTaskService.listTask(qIn);

        Map out = new HashMap();
        out.put("tasks", tasks);

        Integer total = iTaskService.totalTask(qIn);
        out.put("totalTasks", total);

        return out;
    }

    @Override
    public void completeTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);

        Task task = iCommonService.getTaskByTaskId(taskId);

        if (!task.getCreateUserId().equals(userInfo.getUserId())) {
            throw new Exception("10032");
        }

        Map qIn = new HashMap();
        qIn.put("taskId", task.getTaskId());
        qIn.put("status", GogoStatus.COMPLETE);
        qIn.put("endTime", new Date());

        iTaskService.updateTask(qIn);
    }

    @Override
    public Map getTask(Map in) throws Exception {
        String token = in.get("token").toString();
        String taskId = in.get("taskId").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = (String) in.get("keyToken");

        UserInfo userInfo = iCommonService.getUserByToken(token);

        Task task = iCommonService.getTaskByTaskId(taskId);

        if (!userInfo.getUserId().equals(task.getCreateUserId())) {
            throw new Exception("10036");
        }

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = iCommonService.takeNoteAES(keyToken, encryptKey);

        NoteDetail noteDetail=iNoteService.getNoteDetail(task.getTaskId());

        //用AES秘钥加密笔记内容的AES秘钥
        String data = task.getUserEncodeKey();
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        task.setUserEncodeKey(outCode);

        Map out = new HashMap();
        out.put("task", task);
        out.put("noteContent", noteDetail.getContent());

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTask(Map in) throws Exception {
        String token=in.get("token").toString();
        String taskId=in.get("taskId").toString();

        UserInfo userInfo=iCommonService.getUserByToken(token);

        Task task=iCommonService.getTaskByTaskId(taskId);

        if(!task.getCreateUserId().equals(userInfo.getUserId())){
            throw new Exception("10037");
        }

        Map qIn=new HashMap();
        qIn.put("taskId", taskId);
        iTaskService.deleteTask(qIn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(Map in) throws Exception {
        String token=in.get("token").toString();
        String taskId=in.get("taskId").toString();
        String taskTitle=in.get("taskTitle").toString();
        Integer priority=(Integer)in.get("priority");
        String status=(String)in.get("status");
        String taskType=(String)in.get("taskType");
        String important=(String)in.get("important");
        Boolean complete=(Boolean)in.get("complete");
        Date endTime=(Date)in.get("endTime");
        String encryptKey=in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        String content=in.get("content").toString();

        UserInfo userInfo=iCommonService.getUserByToken(token);

        Task task=iCommonService.getTaskByTaskId(taskId);

        if(!userInfo.getUserId().equals(task.getCreateUserId())){
            throw new Exception("10037");
        }

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = iCommonService.takeNoteAES(keyToken, encryptKey);

        /**
         * 保存task
         */
        Map qIn=new HashMap();
        qIn.put("taskId", taskId);
        qIn.put("taskTitle", taskTitle);
        qIn.put("priority", priority);
        qIn.put("status", status);
        qIn.put("taskType", taskType);
        qIn.put("important", important);
        qIn.put("complete", complete);
        qIn.put("endTime", endTime);
        qIn.put("userEncodeKey", strAESKey);
        iTaskService.updateTask(qIn);

        /**
         * 保存content
         */
        NoteDetail noteDetail=iNoteService.getNoteDetail(taskId);
        noteDetail.setContent(content);
        qIn=new HashMap();
        qIn.put("contentId", noteDetail.getContentId());
        qIn.put("content", content);
        iNoteService.updateNoteDetail(qIn);
    }
}
