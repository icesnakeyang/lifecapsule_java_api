package com.gogoyang.lifecapsule.meta.publicNote.dao;

import com.gogoyang.lifecapsule.meta.publicNote.entity.PublicNote;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface PublicNoteDao {
    void createPublicNote(PublicNote publicNote);

    PublicNote getPublicNote(Map qIn);

    ArrayList<PublicNote> listPublicNote(String userId);

    PublicNote getPublicNoteByNoteId(String noteId);

    void updatePublicNote(PublicNote publicNote);
}
