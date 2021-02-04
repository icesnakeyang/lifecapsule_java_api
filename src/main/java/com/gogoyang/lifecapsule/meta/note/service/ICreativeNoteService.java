package com.gogoyang.lifecapsule.meta.note.service;

import com.gogoyang.lifecapsule.meta.note.entity.CreativeNote;

import java.util.ArrayList;
import java.util.Map;

public interface ICreativeNoteService {
    /**
     * 创建一个创新防拖延笔记
     * @param creativeNote
     */
    void createCreativeNote(CreativeNote creativeNote);

    /**
     * 查询创新方拖延笔记列表
     * @param qIn
     * noteId
     * @return
     */
    ArrayList<CreativeNote> listCreativeNote(Map qIn);
}
