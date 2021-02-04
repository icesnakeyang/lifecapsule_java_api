package com.gogoyang.lifecapsule.meta.note.dao;

import com.gogoyang.lifecapsule.meta.note.entity.CreativeNote;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CreativeNoteDao {

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

    /**
     * 删除创新方拖延笔记
     * @param qIn
     * noteId
     */
    void deleteCreativeNote(Map qIn);

    /**
     * 根据creativeNoteId修改笔记内容
     * @param qIn
     * content
     * creativeNoteId
     */
    void updateCreativeNoteDetail(Map qIn);
}
