package com.gogoyang.lifecapsule.meta.note.service;

import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;

import java.util.List;
import java.util.Map;

public interface INoteService {
    /**
     * 创建一个笔记
     *
     * @param noteInfo
     * @throws Exception
     */
    void createNote(NoteInfo noteInfo) throws Exception;

    /**
     * 修改一个笔记
     *
     * @param qIn
     * title
     * categoryId
     * userEncodeKey
     * noteId（查询条件）
     * content
     * 删除detail，再新增到detail表
     * @throws Exception
     */
    void updateNote(Map qIn) throws Exception;

    /**
     * 删除笔记
     *
     * @param noteId
     * @throws Exception
     */
    void deleteNote(String noteId) throws Exception;

    /**
     * 读取一个笔记简要信息
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    NoteInfo getNoteTinyByNoteId(String noteId) throws Exception;

    /**
     * 读取一个笔记，包括详细内容
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    NoteInfo getNoteDetailByNoteId(String noteId) throws Exception;

    /**
     * 读取一个用户的所有笔记列表（简要信息）
     *
     * @param userId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<NoteInfo> listNoteByUserId(String userId, Integer offset, Integer pageSize) throws Exception;


    /**
     * 统计一个笔记分类下的笔记总数
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    Integer countNoteByCategoryId(String categoryId) throws Exception;

    /**
     * 查询一个笔记分类下的笔记列表
     *
     * @param categoryId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<NoteInfo> listNoteByCategory(String categoryId, String userId, Integer offset, Integer pageSize) throws Exception;

    void fractureDetail(NoteDetail noteDetail) throws Exception;

    Integer totalNote(String categoryId, String userId) throws Exception;

    void createNoteDetail(NoteDetail noteDetail) throws Exception;
}
