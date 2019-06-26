package com.gogoyang.lifecapsule.meta.note.service;

import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.sun.tools.javap.TypeAnnotationWriter;

import java.util.List;

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
     * @param noteInfo
     * @throws Exception
     */
    void updateNote(NoteInfo noteInfo) throws Exception;

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
}
