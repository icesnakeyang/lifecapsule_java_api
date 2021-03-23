package com.gogoyang.lifecapsule.business.note;


import java.util.Map;

public interface INoteBusinessService {
    /**
     * 创建一个笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map createNote(Map in) throws Exception;

    /**
     * 查询用户的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listNote(Map in) throws Exception;

    Map getNoteDetailByNoteId(Map in) throws Exception;

    void updateNote(Map in) throws Exception;

    void deleteNoteByNoteId(Map in) throws Exception;

    Map getNoteTiny(Map in) throws Exception;

    void moveNoteCategory(Map in) throws Exception;
}
