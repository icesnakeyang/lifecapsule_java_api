package com.gogoyang.lifecapsule.business.note.creativeNote;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import com.gogoyang.lifecapsule.meta.category.service.ICategoryService;
import com.gogoyang.lifecapsule.meta.note.entity.CreativeNote;
import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.ICreativeNoteService;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.security.service.ISecurityService;
import com.gogoyang.lifecapsule.meta.task.entity.Task;
import com.gogoyang.lifecapsule.meta.task.service.ITaskService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import com.gogoyang.lifecapsule.utility.constant.GogoStatus;
import com.gogoyang.lifecapsule.utility.constant.NoteType;
import com.gogoyang.lifecapsule.utility.constant.TaskType;
import com.gogoyang.lifecapsule.utility.constant.TaskZone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CreativeNoteBService implements ICreativeNoteBService {
    private final ISecurityService iSecurityService;
    private final IUserInfoService iUserInfoService;
    private final ICategoryService iCategoryService;
    private final INoteService iNoteService;
    private final ICreativeNoteService iCreativeNoteService;
    private final ICommonService iCommonService;
    private final ITaskService iTaskService;

    public CreativeNoteBService(ISecurityService iSecurityService,
                                IUserInfoService iUserInfoService,
                                ICategoryService iCategoryService,
                                INoteService iNoteService,
                                ICreativeNoteService iCreativeNoteService,
                                ICommonService iCommonService,
                                ITaskService iTaskService) {
        this.iSecurityService = iSecurityService;
        this.iUserInfoService = iUserInfoService;
        this.iCategoryService = iCategoryService;
        this.iNoteService = iNoteService;
        this.iCreativeNoteService = iCreativeNoteService;
        this.iCommonService = iCommonService;
        this.iTaskService = iTaskService;
    }

    /**
     * 保存创新防拖延笔记
     *
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map saveCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String detail1 = (String) in.get("detail1");
        String detail2 = (String) in.get("detail2");
        String detail3 = (String) in.get("detail3");
        String noteId = (String) in.get("noteId");
        String categoryId = (String) in.get("categoryId");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        ArrayList<Map> tasksMap = (ArrayList<Map>) in.get("tasks");
        String noteTitle = (String) in.get("noteTitle");

        /**
         * 根据token取用户信息
         */
        if (token == null) {
            throw new Exception("10010");
        }
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String strAESKey = iCommonService.takeNoteAES(keyToken, encryptKey);

        /**
         * 保存笔记
         * 4个方拖延子笔记，仍然需要一个note父笔记
         */
        NoteInfo noteInfo = null;
        ArrayList creativeNotes = new ArrayList();
        Map out=new HashMap();
        if (noteId != null) {
            /**
             * 读取原笔记
             */
            noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
            if (noteInfo == null) {
                /**
                 * 笔记不存在
                 */
                throw new Exception("10004");
            }
            Map qIn = new HashMap();
            qIn.put("noteId", noteId);
            creativeNotes = iCreativeNoteService.listCreativeNote(qIn);
            if (creativeNotes.size() == 0) {
                /**
                 * 笔记内容不存在
                 */
                throw new Exception("10004");
            }
            /**
             * 检查是否是用户自己创建的笔记
             */
            if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
                throw new Exception("10011");
            }
            /**
             * 修改noteInfo
             * 首先修改noteInfo的AES秘钥
             * 然后修改3个noteDetail
             * 最后修改10秒任务
             */
            qIn = new HashMap();
            qIn.put("noteId", noteInfo.getNoteId());
            qIn.put("userEncodeKey", strAESKey);
            qIn.put("title", noteTitle);
            iNoteService.updateNote(qIn);

            out.put("note", noteInfo);

            for (int i = 0; i < creativeNotes.size(); i++) {
                CreativeNote creativeNote = (CreativeNote) creativeNotes.get(i);
                if (creativeNote.getCreativeType().equals(NoteType.CREATIVE1.toString())) {
                    /**
                     * 昨天高兴的事
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail1);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteService.updateCreativeNoteDetail(qIn);
                }
                if (creativeNote.getCreativeType().equals(NoteType.CREATIVE2.toString())) {
                    /**
                     * 我的感想
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail2);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteService.updateCreativeNoteDetail(qIn);
                }
                if (creativeNote.getCreativeType().equals(NoteType.CREATIVE3.toString())) {
                    /**
                     * 今天要做的事情
                     */
                    qIn = new HashMap();
                    qIn.put("content", detail3);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteService.updateCreativeNoteDetail(qIn);
                }
            }
            /**
             * todo 保存10秒任务
             * 修改
             * 首先根据noteId读出task列表，oldTasks
             * 遍历oldTasks×tasks，如果有taskId，修改，如果没有就删除
             * 遍历前端提交的tasks，如果taskId=null，新增
             */
            qIn.put("noteId", noteInfo.getNoteId());
            ArrayList<Task> tasksDB = iTaskService.listTask(qIn);
            if (tasksDB.size() > 0) {
                //遍历数据库里旧的任务
                for (int iDB = 0; iDB < tasksDB.size(); iDB++) {
                    Task taskDB = tasksDB.get(iDB);
                    //遍历前端提交的任务
                    int ss = 0;
                    for (int iSubmit = 0; iSubmit < tasksMap.size(); iSubmit++) {
                        Map taskMap = tasksMap.get(iSubmit);
                        String taskId = (String) taskMap.get("taskId");
                        if (taskId != null) {
                            if (taskId.equals(taskDB.getTaskId())) {
                                //修改
                                String title = (String) taskMap.get("taskTitle");
                                qIn.put("taskTitle", taskMap.get("taskTitle"));
                                qIn.put("status", taskMap.get("status"));
                                qIn.put("endTime", taskMap.get("endTime"));
                                qIn.put("taskId", taskMap.get("taskId"));
                                qIn.put("complete", taskMap.get("complete"));
                                iTaskService.updateTask(qIn);

                                //匹配成功，ss+1
                                ss++;
                            }
                        }
                    }
                    if (ss == 0) {
                        //遍历完所有提交的任务，没有该task，则删除
                        qIn=new HashMap();
                        qIn.put("taskId", taskDB.getTaskId());
                        iTaskService.deleteTask(qIn);
                    }
                }
            }
            //遍历前端提交的任务，如果taskId==null，就新增
            if (tasksMap != null && tasksMap.size() > 0) {
                createNew10SecTasks(tasksMap, userInfo.getUserId(), noteInfo.getNoteId());
            }
        } else {
            /**
             * 新增笔记
             */
            noteInfo = new NoteInfo();
            noteInfo.setNoteId(GogoTools.UUID().toString());
            noteInfo.setUserId(userInfo.getUserId());
            noteInfo.setCreatedTime(new Date());
            noteInfo.setTitle(noteTitle);
            /**
             * 如果没有分类id，就使用该用户的默认分类id
             */
            if (categoryId == null) {
                NoteCategory category = iCategoryService.getCategoryByCategoryName("Default", userInfo.getUserId());
                categoryId = category.getCategoryId();
            }
            noteInfo.setCategoryId(categoryId);
            //保存用户的AES私钥
            noteInfo.setUserEncodeKey(strAESKey);
            iNoteService.createNote(noteInfo);

            out.put("note", noteInfo);

            CreativeNote creativeNote = new CreativeNote();
            creativeNote.setNoteId(noteInfo.getNoteId());
            NoteDetail noteDetail = new NoteDetail();
            //昨天高兴的事
            creativeNote.setCreativeType(NoteType.CREATIVE1.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID().toString());
            iCreativeNoteService.createCreativeNote(creativeNote);
            //保存详细内容
            noteDetail.setContent(detail1);
            noteDetail.setContentId(GogoTools.UUID().toString());
            noteDetail.setNoteId(creativeNote.getCreativeNoteId());
            iNoteService.createNoteDetail(noteDetail);
            //我的感受
            creativeNote.setCreativeType(NoteType.CREATIVE2.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID().toString());
            iCreativeNoteService.createCreativeNote(creativeNote);
            noteDetail.setContentId(GogoTools.UUID().toString());
            noteDetail.setContent(detail2);
            noteDetail.setNoteId(creativeNote.getCreativeNoteId());
            iNoteService.createNoteDetail(noteDetail);
            //今天要做的事情
            creativeNote.setCreativeType(NoteType.CREATIVE3.toString());
            creativeNote.setCreativeNoteId(GogoTools.UUID().toString());
            iCreativeNoteService.createCreativeNote(creativeNote);
            noteDetail.setContentId(GogoTools.UUID().toString());
            noteDetail.setContent(detail3);
            noteDetail.setNoteId(creativeNote.getCreativeNoteId());
            iNoteService.createNoteDetail(noteDetail);
            /**
             * todo 保存10秒任务
             * 新增就直接新增就行了
             */
            //遍历前端提交的任务，如果taskId==null，就新增
            if (tasksMap.size() > 0) {
                createNew10SecTasks(tasksMap, userInfo.getUserId(), noteInfo.getNoteId());
            }
        }
        return out;
    }

    /**
     * 读取一个创新防拖延笔记内容
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String noteId = in.get("noteId").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = (String) in.get("keyToken");

        /**
         * 获取用户临时上传的加密笔记AES秘钥的AES秘钥
         */
        String strAESKey = iCommonService.takeNoteAES(keyToken, encryptKey);

        /**
         * 1、检查token，查询登录用户
         * 2、根据noteId，查询note详情
         * 3、比较note的userId是否是登录用户，如果不是，返回错误
         * 4、如果note是当前用户创建的，返回note
         */

        if (token == null) {
            throw new Exception("10010");
        }

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        //查询note简介
        NoteInfo noteInfo = iNoteService.getNoteDetailByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        //检查是否是用户自己的笔记
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }
        Map out = new HashMap();
        out.put("note", noteInfo);
        /**
         * 读取创新防拖延笔记
         */
        Map qIn = new HashMap();
        qIn.put("noteId", noteInfo.getNoteId());
        ArrayList<CreativeNote> creativeNotes = iCreativeNoteService.listCreativeNote(qIn);
        for (int i = 0; i < creativeNotes.size(); i++) {
            NoteDetail noteDetail = iNoteService.getNoteDetail(creativeNotes.get(i).getCreativeNoteId());
            creativeNotes.get(i).setContent(noteDetail.getContent());
        }
        out.put("creativeNoteList", creativeNotes);

        //用AES秘钥加密笔记内容的AES秘钥
        String data = noteInfo.getUserEncodeKey();
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        noteInfo.setUserEncodeKey(outCode);

        /**
         * 读取10秒行动任务
         */
        qIn = new HashMap();
        qIn.put("noteId", noteInfo.getNoteId());
        ArrayList<Task> tasks = iTaskService.listTask(qIn);
        out.put("taskList", tasks);

        return out;
    }

    private void createNew10SecTasks(ArrayList<Map> tasksMap, String userId, String noteId) throws Exception {
        for (int j = 0; j < tasksMap.size(); j++) {
            String taskId=(String)tasksMap.get(j).get("taskId");
            if(taskId!=null){
                continue;
            }
            Map taskMap = tasksMap.get(j);
            String taskTitle = (String) taskMap.get("taskTitle");
            if (taskTitle == null || taskTitle.equals("")) {
                //任务标题不能为空
                throw new Exception("10033");
            }
            Boolean complete = (Boolean) taskMap.get("complete");
            Task task = new Task();
            task.setCreateTime(new Date());
            task.setCreateUserId(userId);
            task.setNoteId(noteId);
            task.setStatus(GogoStatus.PROGRESS.toString());
            task.setTaskId(GogoTools.UUID().toString());
            task.setTaskTitle(taskTitle);
            task.setTaskType(TaskType.ACTION_10_SEC.toString());
            /**
             * 优先级默认为1，如果以后要增加优先级，就调大这个值，值越高，优先级越高
             */
            task.setPriority(1);
            /**
             * 任务的象限
             * 默认为重要且紧急的任务
             */
            task.setImportant(TaskZone.IMPORTANT_AND_URGENT.toString());
            task.setComplete(complete);
            iTaskService.createTask(task);
        }
    }
}
