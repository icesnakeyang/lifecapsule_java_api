package com.gogoyang.lifecapsule.business.note;


import java.util.Map;

public interface INoteBusinessService {
    /**
     * 创建一个笔记
     * @param in
     * @return
     * @throws Exception
     */
    Map createNote(Map in) throws Exception;

    /**
     * 查询用户的笔记列表
     * @param in
     * @return
     * @throws Exception
     */
    Map listNoteByUserToken(Map in) throws Exception;

    /**
     * 查询一个笔记分类下的笔记列表
     * @param in
     * @return
     * @throws Exception
     */
    Map listNoteByCategory(Map in) throws Exception;

    Map getNoteDetailByNoteId(Map in) throws Exception;

    Map updateNote(Map in) throws Exception;
}
