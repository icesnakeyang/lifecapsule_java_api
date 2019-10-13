package com.gogoyang.lifecapsule.business.common;

import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;

public interface ICommonService {
    UserInfo getUserByToken(String token) throws Exception;

    Trigger getTriggerByTriggerId(String triggerId) throws Exception;

    NoteInfo getNoteByNoteId(String noteId) throws Exception;
}
