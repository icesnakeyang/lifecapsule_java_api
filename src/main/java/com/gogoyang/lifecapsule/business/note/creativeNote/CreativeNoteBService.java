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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreativeNoteBService implements ICreativeNoteBService{
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
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String detail1 = (String) in.get("detail1");
        String detail2 = (String) in.get("detail2");
        String detail3 = (String) in.get("detail3");
        String noteId=(String)in.get("noteId");
        String categoryId = (String) in.get("categoryId");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        ArrayList tasks= (ArrayList<String>)in.get("tasks");

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
        NoteInfo noteInfo=null;
        ArrayList creativeNotes=new ArrayList();
        if(noteId!=null){
            /**
             * 读取原笔记
             */
            noteInfo=iNoteService.getNoteTinyByNoteId(noteId);
            if(noteInfo==null){
                /**
                 * 笔记不存在
                 */
                throw new Exception("10004");
            }
            Map qIn=new HashMap();
            qIn.put("noteId", noteId);
            creativeNotes =iCreativeNoteService.listCreativeNote(qIn);
            if(creativeNotes.size()==0) {
                /**
                 * 笔记内容不存在
                 */
                throw new Exception("10004");
            }
            /**
             * 检查是否是用户自己创建的笔记
             */
            if(!noteInfo.getUserId().equals(userInfo.getUserId())){
                throw new Exception("10011");
            }
            /**
             * 修改noteInfo
             * 首先修改noteInfo的AES秘钥
             * 然后修改3个noteDetail
             * 最后修改10秒任务
             */
            qIn=new HashMap();
            qIn.put("noteId",noteInfo.getNoteId());
            qIn.put("userEncodeKey",strAESKey);
            iNoteService.updateNote(qIn);

            for(int i=0;i<creativeNotes.size();i++){
                CreativeNote creativeNote=(CreativeNote)creativeNotes.get(i);
                if(creativeNote.getCreativeType().equals(NoteType.CREATIVE1)){
                    /**
                     * 昨天高兴的事
                     */
                    qIn=new HashMap();
                    qIn.put("content", detail1);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteService.updateCreativeNoteDetail(qIn);
                }
                if(creativeNote.getCreativeType().equals(NoteType.CREATIVE2)){
                    /**
                     * 我的感想
                     */
                    qIn=new HashMap();
                    qIn.put("content", detail2);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteService.updateCreativeNoteDetail(qIn);
                }
                if(creativeNote.getCreativeType().equals(NoteType.CREATIVE3)){
                    /**
                     * 今天要做的事情
                     */
                    qIn=new HashMap();
                    qIn.put("content", detail3);
                    qIn.put("creativeNoteId", creativeNote.getCreativeNoteId());
                    iCreativeNoteService.updateCreativeNoteDetail(qIn);
                }
            }
            /**
             * todo 保存10秒任务
             * 修改
             * 首先根据noteId读出task列表，oldTasks
             * 遍历前端提交的tasks，如果oldTasks的taskId，在tasks里不存在，就删除
             * 如果，
             */
            qIn.put("noteId", creativeNote.)
            iTaskService.listTask(qIn);
            if(tasks.size()>0){
                for(int i=0;i<tasks.size();i++){
                    Task task=new Task();
                    task.setCreateTime(new Date());
                    task.setCreateUserId(userInfo.getUserId());
                    task.setStatus(GogoStatus.ACTIVE.toString());
                    task.setTaskId(GogoTools.UUID().toString());
                    task.setTaskType(TaskType.ACTION_10_SEC.toString());
                    task.setNoteId(noteInfo.getNoteId());
                    iTaskService.createTask(task);
                    //保存内容
                    iNot
                    NoteDetail noteDetail=iNoteService.getNoteDetail()
                    noteDetail.setNoteId(task.getTaskId());
                    noteDetail.setContent(tasks.get(i).toString());
                    noteDetail.setContentId(GogoTools.UUID().toString());
                    qIn=new HashMap();
                    qIn.put("contentId", )

                    iNoteService.updateNoteDetail(qIn);
                }
            }
        }else {
            /**
             * 新增笔记
             */
            noteInfo = new NoteInfo();
            noteInfo.setNoteId(GogoTools.UUID().toString());
            noteInfo.setUserId(userInfo.getUserId());
            noteInfo.setCreatedTime(new Date());
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

            CreativeNote creativeNote = new CreativeNote();
            creativeNote.setNoteId(noteInfo.getNoteId());
            NoteDetail noteDetail=new NoteDetail();
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
             */
        }
    }

    /**
     * 读取一个创新防拖延笔记内容
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
        Map qIn=new HashMap();
        qIn.put("noteId", noteInfo.getNoteId());
        ArrayList<CreativeNote> creativeNotes=iCreativeNoteService.listCreativeNote(qIn);
        for(int i=0;i<creativeNotes.size();i++){
            NoteDetail noteDetail=iNoteService.getNoteDetail(creativeNotes.get(i).getCreativeNoteId());
            creativeNotes.get(i).setContent(noteDetail.getContent());
        }
        out.put("creativeNoteList", creativeNotes);

        //用AES秘钥加密笔记内容的AES秘钥
        String data = noteInfo.getUserEncodeKey();
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        noteInfo.setUserEncodeKey(outCode);

        return out;
    }
}
