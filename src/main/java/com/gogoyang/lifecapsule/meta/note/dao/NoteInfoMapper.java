package com.gogoyang.lifecapsule.meta.note.dao;

import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoteInfoMapper {
    /**
     * 创建一个笔记
     * @param noteInfo
     */
    void createNoteInfo(NoteInfo noteInfo);

    /**
     * 分页查询一个用户id的笔记列表
     * @param qIn
     * @return
     */
    List listNoteByUserId(Map qIn);

    /**
     * 根据noteId查询note笔记
     * @param noteId
     * @return
     */
    NoteInfo getNoteByNoteId(String noteId);

    /**
     * 修改note的基本信息
     * @param qIn
     * title
     * categoryId
     * userEncodeKey
     * noteId
     */
    void updateNoteInfo(Map qIn);

    /**
     * 统计一个笔记分类下的笔记总数
     * @param categoryId
     */
    Integer totalNoteByCategoryId(String categoryId);

    /**
     * 查询一个笔记分类下的笔记列表
     * @param qIn
     * @return
     */
    List listNoteByCategory(Map qIn);

    void createNoteDetail(NoteDetail noteDetail);

    void updateNoteDetail(NoteDetail noteDetail);

    void deleteNoteDetail(String noteId);

    NoteDetail getNoteDetail(String noteId);

    void deleteNoteInfo(String noteId);

    Integer totalNote(Map qIn);

}
