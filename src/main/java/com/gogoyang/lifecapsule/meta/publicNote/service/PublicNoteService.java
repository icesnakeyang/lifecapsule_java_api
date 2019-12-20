package com.gogoyang.lifecapsule.meta.publicNote.service;

import com.gogoyang.lifecapsule.meta.publicNote.dao.PublicNoteDao;
import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class PublicNoteService implements IPublicNoteService {
    private final PublicNoteDao publicNoteDao;

    public PublicNoteService(PublicNoteDao publicNoteDao) {
        this.publicNoteDao = publicNoteDao;
    }

    @Override
    public void createPublicNote(PublicNote publicNote) throws Exception {
        publicNoteDao.createPublicNote(publicNote);
    }

    @Override
    public PublicNote getPublicNote(Map qIn) throws Exception {
        PublicNote publicNote = publicNoteDao.getPublicNote(qIn);
        return publicNote;
    }

    @Override
    public ArrayList<PublicNote> listPublicNote(String userId) throws Exception {
        ArrayList<PublicNote> publicNotes = publicNoteDao.listPublicNote(userId);
        return publicNotes;
    }

    @Override
    public PublicNote getPublicNoteByNoteId(String noteId) throws Exception {
        PublicNote publicNote = publicNoteDao.getPublicNoteByNoteId(noteId);
        return publicNote;
    }
}
