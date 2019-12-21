package com.gogoyang.lifecapsule.business.publicNote;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;
import com.gogoyang.lifecapsule.meta.publicNote.service.IPublicNoteService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PublicNoteBusinessService implements IPublicNoteBusinessService {
    private final ICommonService iCommonService;
    private final INoteService iNoteService;
    private final IPublicNoteService iPublicNoteService;

    public PublicNoteBusinessService(ICommonService iCommonService,
                                     INoteService iNoteService,
                                     IPublicNoteService iPublicNoteService) {
        this.iCommonService = iCommonService;
        this.iNoteService = iNoteService;
        this.iPublicNoteService = iPublicNoteService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = (String) in.get("title");
        String content = (String) in.get("content");

        UserInfo userInfo = iCommonService.getUserByToken(token);

        NoteInfo noteInfo = iCommonService.getNoteByNoteId(noteId, userInfo.getUserId());

        noteInfo = iNoteService.getNoteDetailByNoteId(noteId);

        PublicNote publicNote = new PublicNote();
        if (content != null) {
            publicNote.setContent(content);
        } else {
            publicNote.setContent(noteInfo.getDetail());
        }
        publicNote.setCreateTime(new Date());
        if (title != null) {
            publicNote.setTitle(title);
        } else {
            publicNote.setTitle(noteInfo.getTitle());
        }
        publicNote.setNoteId(noteInfo.getNoteId());
        publicNote.setUserId(userInfo.getUserId());

        iPublicNoteService.createPublicNote(publicNote);
    }

    @Override
    public Map listPublicNote(Map in) throws Exception {
        String token = in.get("token").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);

        ArrayList<PublicNote> publicNotes = iPublicNoteService.listPublicNote(userInfo.getUserId());

        Map out = new HashMap();
        out.put("publicNotes", publicNotes);

        return out;
    }

    @Override
    public Map getPublicNote(Map in) throws Exception {
        String noteId = in.get("noteId").toString();

        PublicNote publicNote = iPublicNoteService.getPublicNoteByNoteId(noteId);

        Map out = new HashMap();
        out.put("note", publicNote);
        return out;
    }

    @Override
    public void updatePublicNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = in.get("title").toString();
        String content = in.get("content").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);

        PublicNote publicNote = iCommonService.getPublicNote(noteId);

        if (!userInfo.getUserId().equals(publicNote.getUserId())) {
            throw new Exception("10030");
        }

        publicNote.setTitle(title);
        publicNote.setContent(content);
        publicNote.setLastTime(new Date());
        iPublicNoteService.updatePublicNote(publicNote);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void fixBug(Map in) throws Exception {
        iPublicNoteService.fixBug(in);
    }
}
