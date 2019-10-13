package com.gogoyang.lifecapsule.business.common;

import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.trigger.service.ITriggerService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService implements ICommonService {
    private final IUserInfoService iUserInfoService;
    private final ITriggerService iTriggerService;
    private final INoteService iNoteService;

    public CommonService(IUserInfoService iUserInfoService,
                         ITriggerService iTriggerService,
                         INoteService iNoteService) {
        this.iUserInfoService = iUserInfoService;
        this.iTriggerService = iTriggerService;
        this.iNoteService = iNoteService;
    }

    @Override
    public UserInfo getUserByToken(String token) throws Exception {
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }
        return userInfo;
    }

    @Override
    public Trigger getTriggerByTriggerId(String triggerId) throws Exception {
        Trigger trigger = iTriggerService.getTriggerByTriggerId(triggerId);
        if (trigger == null) {
            throw new Exception("10017");
        }
        return trigger;
    }

    @Override
    public NoteInfo getNoteByNoteId(String noteId) throws Exception {
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10023");
        }
        return noteInfo;
    }
}
