package com.gogoyang.lifecapsule.meta.note.service;

import com.gogoyang.lifecapsule.meta.note.dao.CreativeNoteDao;
import com.gogoyang.lifecapsule.meta.note.entity.CreativeNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CreativeNoteService implements ICreativeNoteService {
    private final CreativeNoteDao creativeNoteDao;

    public CreativeNoteService(CreativeNoteDao creativeNoteDao) {
        this.creativeNoteDao = creativeNoteDao;
    }

    @Override
    public void createCreativeNote(CreativeNote creativeNote) {
        creativeNoteDao.createCreativeNote(creativeNote);
    }

    @Override
    public ArrayList<CreativeNote> listCreativeNote(Map qIn) {
        ArrayList<CreativeNote> creativeNotes = creativeNoteDao.listCreativeNote(qIn);
        return creativeNotes;
    }

    /**
     * 删除创新方拖延笔记
     * @param qIn
     * noteId
     */
    @Override
    public void deleteCreativeNote(Map qIn) throws Exception {
        creativeNoteDao.deleteCreativeNote(qIn);
    }

    @Override
    public void updateCreativeNoteDetail(Map qIn) {
        creativeNoteDao.updateCreativeNoteDetail(qIn);
    }
}
