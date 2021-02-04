package com.gogoyang.lifecapsule.business.common;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;
import com.gogoyang.lifecapsule.meta.task.entity.Task;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;

public interface ICommonService {
    UserInfo getUserByToken(String token) throws Exception;

    Trigger getTriggerByTriggerId(String triggerId) throws Exception;

    NoteInfo getNoteByNoteId(String noteId) throws Exception;

    NoteInfo getNoteByNoteId(String noteId, String userId) throws Exception;

    GogoKey getPublicKey(String gogoPublicKey) throws Exception;

    PublicNote getPublicNote(String noteId) throws Exception;

    /**
     * 获取用户上传的AES秘钥
     * 该秘钥用于加密解密用户笔记的AES，加密后传递到用户客户端
     * @param keyToken
     * @param encryptKey
     * @return
     * @throws Exception
     */
    String takeNoteAES(String keyToken, String encryptKey) throws Exception;

    Task getTaskByTaskId(String taskId) throws Exception;

}
