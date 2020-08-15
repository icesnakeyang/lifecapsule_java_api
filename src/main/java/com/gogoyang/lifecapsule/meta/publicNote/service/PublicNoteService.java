package com.gogoyang.lifecapsule.meta.publicNote.service;

import com.gogoyang.lifecapsule.meta.publicNote.dao.PublicNoteDao;
import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;
import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNoteDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Service
public class PublicNoteService implements IPublicNoteService {
    private final PublicNoteDao publicNoteDao;

    public PublicNoteService(PublicNoteDao publicNoteDao) {
        this.publicNoteDao = publicNoteDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createPublicNote(PublicNote publicNote) throws Exception {
        //保存简要信息
        publicNoteDao.createPublicNote(publicNote);

        //保存详细内容
        PublicNoteDetail publicNoteDetail = new PublicNoteDetail();
        publicNoteDetail.setContent(publicNote.getContent());
        publicNoteDetail.setNoteId(publicNote.getNoteId());
        publicNoteDao.createPublicNoteDetail(publicNoteDetail);
    }

    /**
     * 查询一个笔记的简要信息
     *
     * @param qIn
     * @return
     * @throws Exception
     */
    @Override
    public PublicNote getPublicNote(Map qIn) throws Exception {
        PublicNote publicNote = publicNoteDao.getPublicNote(qIn);
        return publicNote;
    }

    /**
     * 查询多个笔记的简要信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<PublicNote> listPublicNote(String userId) throws Exception {
        ArrayList<PublicNote> publicNotes = publicNoteDao.listPublicNote(userId);
        return publicNotes;
    }

    /**
     * 查询一个笔记的详细信息
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    @Override
    public PublicNote getPublicNoteByNoteId(String noteId) throws Exception {
        //先查询简要信息
        PublicNote publicNote = publicNoteDao.getPublicNoteByNoteId(noteId);

        //查询详细内容
        PublicNoteDetail publicNoteDetail = publicNoteDao.getPublicNoteDetail(noteId);

        publicNote.setContent(publicNoteDetail.getContent());

        return publicNote;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void fixBug(Map in) throws Exception {
        ArrayList<PublicNote> publicNotes = publicNoteDao.listAllPublicNote();
        for (int i = 0; i < publicNotes.size(); i++) {
            PublicNote note = publicNotes.get(i);
            PublicNoteDetail publicNoteDetail = new PublicNoteDetail();
            publicNoteDetail.setNoteId(note.getNoteId());
            publicNoteDetail.setContent(note.getContent());
            publicNoteDao.createPublicNoteDetail(publicNoteDetail);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePublicNote(PublicNote publicNote) throws Exception {
        publicNoteDao.updatePublicNote(publicNote);
        PublicNoteDetail publicNoteDetail=new PublicNoteDetail();
        publicNoteDetail.setNoteId(publicNote.getNoteId());
        publicNoteDetail.setContent(publicNote.getContent());
        publicNoteDao.updatePublicNoteDetail(publicNoteDetail);
    }
}
