package com.gogoyang.lifecapsule.meta.publicNote.service;

import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;

import java.util.ArrayList;
import java.util.Map;

public interface IPublicNoteService {
    void createPublicNote(PublicNote publicNote) throws Exception;

    PublicNote getPublicNote(Map qIn) throws Exception;

    ArrayList<PublicNote> listPublicNote(String userId) throws Exception;

    PublicNote getPublicNoteByNoteId(String noteId) throws Exception;

    void fixBug(Map in) throws Exception;

    void updatePublicNote(PublicNote publicNote) throws Exception;
}
