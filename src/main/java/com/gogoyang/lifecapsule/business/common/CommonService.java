package com.gogoyang.lifecapsule.business.common;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;
import com.gogoyang.lifecapsule.meta.publicNote.service.IPublicNoteService;
import com.gogoyang.lifecapsule.meta.security.service.ISecurityService;
import com.gogoyang.lifecapsule.meta.task.entity.Task;
import com.gogoyang.lifecapsule.meta.task.service.ITaskService;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.trigger.service.ITriggerService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommonService implements ICommonService {
    private final IUserInfoService iUserInfoService;
    private final ITriggerService iTriggerService;
    private final INoteService iNoteService;
    private final IGogoKeyService iGogoKeyService;
    private final IPublicNoteService iPublicNoteService;
    private final ISecurityService iSecurityService;
    private final ITaskService iTaskService;

    public CommonService(IUserInfoService iUserInfoService,
                         ITriggerService iTriggerService,
                         INoteService iNoteService,
                         IGogoKeyService iGogoKeyService,
                         IPublicNoteService iPublicNoteService,
                         ISecurityService iSecurityService,
                         ITaskService iTaskService) {
        this.iUserInfoService = iUserInfoService;
        this.iTriggerService = iTriggerService;
        this.iNoteService = iNoteService;
        this.iGogoKeyService = iGogoKeyService;
        this.iPublicNoteService = iPublicNoteService;
        this.iSecurityService = iSecurityService;
        this.iTaskService = iTaskService;
    }

    /**
     * 根据token读取并检查userInfo，如果没有返回错误10003
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo getUserByToken(String token) throws Exception {
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }
        return userInfo;
    }

    /**
     * 根据triggerId读取trigger，如果没有返回错误10017
     * 此方法用于确定存在trigger的情况，普通查询不适用
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    @Override
    public Trigger getTriggerByTriggerId(String triggerId) throws Exception {
        Trigger trigger = iTriggerService.getTriggerByTriggerId(triggerId);
        if (trigger == null) {
            throw new Exception("10017");
        }
        return trigger;
    }

    /**
     * 根据noteId读取note，如果没有返回错误10023
     * 同样用于检查note必须存在的情况，不适用普通查询
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    @Override
    public NoteInfo getNoteByNoteId(String noteId) throws Exception {
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10023");
        }
        return noteInfo;
    }

    /**
     * 读取note后，再检查note是否为当前userId创建的，错误返回10024
     *
     * @param noteId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public NoteInfo getNoteByNoteId(String noteId, String userId) throws Exception {
        NoteInfo noteInfo = getNoteByNoteId(noteId);
        if (!noteInfo.getUserId().equals(userId)) {
            throw new Exception("10024");
        }
        return noteInfo;
    }

    @Override
    public GogoKey getPublicKey(String gogoPublicKey) throws Exception {
        GogoKey publicKey = iGogoKeyService.getPublicKey(gogoPublicKey);
        if (publicKey == null) {
            throw new Exception("10022");
        }
        return publicKey;
    }

    @Override
    public PublicNote getPublicNote(String noteId) throws Exception {
        PublicNote publicNote = iPublicNoteService.getPublicNoteByNoteId(noteId);
        if (publicNote == null) {
            throw new Exception("10029");
        }
        return publicNote;
    }

    /**
     * 获取用户上传的AES秘钥
     * 该秘钥用于加密解密用户笔记的AES，加密后传递到用户客户端
     *
     * @param keyToken
     * @param encryptKey
     * @return
     * @throws Exception
     */
    @Override
    public String takeNoteAES(String keyToken, String encryptKey) throws Exception {
        //读取生成的RSA私钥
        String privateKey = iSecurityService.getRSAKey(keyToken);
        //用私钥解密用户上传的AES秘钥
        String strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        //删除RSA私钥
        iSecurityService.deleteRSAKey(keyToken);
        return strAESKey;
    }

    @Override
    public Task getTaskByTaskId(String taskId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("taskId", taskId);
        Task task = iTaskService.getTask(qIn);
        if (task == null) {
            throw new Exception("10031");
        }
        return task;
    }
}
