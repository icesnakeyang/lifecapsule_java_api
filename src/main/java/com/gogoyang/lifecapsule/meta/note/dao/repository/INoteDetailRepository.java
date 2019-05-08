package com.gogoyang.lifecapsule.meta.note.dao.repository;

import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;

public interface INoteDetailRepository {
    /**
     * 创建一个笔记的详细信息
     *
     * @param noteDetail
     * @throws Exception
     */
    void createNoteDetail(NoteDetail noteDetail) throws Exception;

    /**
     * 修改笔记的详细信息
     *
     * @param noteDetail
     * @throws Exception
     */
    void updateNoteDetail(NoteDetail noteDetail) throws Exception;

    /**
     * 删除笔记的详细信息
     *
     * @param noteId
     * @throws Exception
     */
    void deleteNoteDetail(String noteId) throws Exception;

    /**
     * 读取笔记的详细信息
     *
     * @param noteId
     * @throws Exception
     */
    NoteDetail getNoteDetail(String noteId) throws Exception;
}
