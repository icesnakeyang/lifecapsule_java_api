package com.gogoyang.lifecapsule.meta.note.service;

import com.gogoyang.lifecapsule.meta.note.dao.NoteInfoMapper;
import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService implements INoteService {
    private final NoteInfoMapper noteInfoMapper;

    @Autowired
    public NoteService(NoteInfoMapper noteInfoMapper) {
        this.noteInfoMapper = noteInfoMapper;
    }

    /**
     * 创建一个笔记
     *
     * @param noteInfo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNote(NoteInfo noteInfo) throws Exception {
        /**
         * 首先新增noteinfo，获取到noteId，然后保存noteDetail
         */
        noteInfoMapper.createNoteInfo(noteInfo);

        if (noteInfo.getDetail() == null) {
            return;
        }
        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNoteId(noteInfo.getNoteId());
        noteDetail.setContent(noteInfo.getDetail());
        /**
         * 这里添加把detail打碎，加密的算法
         */
        fractureDetail(noteDetail);
        noteInfoMapper.createNoteDetail(noteDetail);
    }

    /**
     * 修改一个笔记
     *
     * @param qIn title
     *            categoryId
     *            userEncodeKey
     *            noteId（查询条件）
     *            content
     *            删除detail，再新增到detail表
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNote(Map qIn) throws Exception {
        //修改NoteInfo表
        noteInfoMapper.updateNoteInfo(qIn);

        //修改NoteDetail
        String noteId = qIn.get("noteId").toString();
        String content = (String)qIn.get("content");
        if (content == null) {
            //没有内容就不修改
            return;
        }
//        fractureDetail(noteDetail);
        /**
         * todo
         * 在碎片化功能实现之前，直接删除旧的，创建新的
         */
        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setContentId(GogoTools.UUID().toString());
        noteDetail.setContent(content);
        noteDetail.setNoteId(noteId);
        if (noteId == null) {
            throw new Exception("10004");
        }
        noteInfoMapper.deleteNoteDetail(noteId);
        noteInfoMapper.createNoteDetail(noteDetail);
    }

    /**
     * 删除笔记
     *
     * @param noteId
     * @throws Exception
     */
    @Override
    public void deleteNote(String noteId) throws Exception {
        noteInfoMapper.deleteNoteInfo(noteId);
        noteInfoMapper.deleteNoteDetail(noteId);
    }

    /**
     * 读取一个笔记简要信息
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    @Override
    public NoteInfo getNoteTinyByNoteId(String noteId) throws Exception {
        NoteInfo noteInfo = noteInfoMapper.getNoteByNoteId(noteId);
        return noteInfo;
    }

    /**
     * 读取一个笔记，包括详细内容
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    @Override
    public NoteInfo getNoteDetailByNoteId(String noteId) throws Exception {
        NoteInfo noteInfo = noteInfoMapper.getNoteByNoteId(noteId);
        if (noteInfo == null) {
            return null;
        }
        /**
         * 没有实现碎片功能前，暂时直接读取
         */
        NoteDetail noteDetail = noteInfoMapper.getNoteDetail(noteId);
        if (noteDetail != null) {
            noteInfo.setDetail(noteDetail.getContent());
        }
        return noteInfo;
    }

    /**
     * 读取一个用户的所有笔记列表（简要信息）
     *
     * @param userId   用户id
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteInfo> listNoteByUserId(String userId, Integer offset, Integer pageSize) throws Exception {
        Map qIn = new HashMap();
        qIn.put("userId", userId);
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        List noteList = noteInfoMapper.listNote(qIn);
        return noteList;
    }

    @Override
    public List<NoteInfo> listNote(Map qIn) throws Exception {
        List noteList = noteInfoMapper.listNote(qIn);
        return noteList;
    }

    /**
     * 统计一个笔记分类下的笔记总数
     *
     * @param categoryId
     * @throws Exception
     */
    @Override
    public Integer countNoteByCategoryId(String categoryId) throws Exception {
        Map qIn=new HashMap();
        qIn.put("categoryId", categoryId);
        Integer total = noteInfoMapper.totalNote(qIn);
        return total;
    }

    /**
     * 查询一个笔记分类下的笔记列表
     *
     * @param categoryId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteInfo> listNoteByCategory(String categoryId, String userId, Integer offset, Integer pageSize) throws Exception {
        Map qIn = new HashMap();
        qIn.put("categoryId", categoryId);
        qIn.put("userId", userId);
        qIn.put("offset", offset);
        qIn.put("size", pageSize);
        List noteList = noteInfoMapper.listNote(qIn);
        return noteList;
    }

    @Override
    public void fractureDetail(NoteDetail noteDetail) throws Exception {
        for (int i = 0; i < noteDetail.getContent().length(); i++) {
            /**
             * 打碎，加密，排序，保存
             * 读取一个字节，进行hash256
             */
        }
    }

    @Override
    public Integer totalNote(Map qIn) throws Exception {
        Integer total = noteInfoMapper.totalNote(qIn);
        return total;
    }

    @Override
    public void createNoteDetail(NoteDetail noteDetail) throws Exception {
        noteInfoMapper.createNoteDetail(noteDetail);
    }

    @Override
    public NoteDetail getNoteDetail(String noteId) throws Exception {
        NoteDetail noteDetail = noteInfoMapper.getNoteDetail(noteId);
        return noteDetail;
    }

    @Override
    public void updateNoteDetail(Map qIn) {
        noteInfoMapper.updateNoteDetail(qIn);
    }
}
