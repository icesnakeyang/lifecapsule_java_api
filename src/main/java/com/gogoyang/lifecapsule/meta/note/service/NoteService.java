package com.gogoyang.lifecapsule.meta.note.service;

import com.gogoyang.lifecapsule.meta.note.dao.mapper.NoteInfoMapper;
import com.gogoyang.lifecapsule.meta.note.dao.repository.INoteDetailRepository;
import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService implements INoteService {
    private final NoteInfoMapper noteInfoMapper;
    private final INoteDetailRepository iNoteDetailRepository;

    @Autowired
    public NoteService(NoteInfoMapper noteInfoMapper,
                       INoteDetailRepository iNoteDetailRepository) {
        this.noteInfoMapper = noteInfoMapper;
        this.iNoteDetailRepository = iNoteDetailRepository;
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

        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNoteId(noteInfo.getNoteId());
        noteDetail.setDetail(noteInfo.getDetail());
        /**
         * 这里添加把detail打碎，加密的算法
         */
        fractureDetail(noteDetail);
//        iNoteDetailRepository.createNoteDetail(noteDetail);
    }

    /**
     * 修改一个笔记
     *
     * @param noteInfo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNote(NoteInfo noteInfo) throws Exception {
        //修改NoteInfo表
        noteInfoMapper.updateNoteInfo(noteInfo);

        //修改NoteDetail
        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNoteId(noteInfo.getNoteId());
        noteDetail.setDetail(noteInfo.getDetail());
        fractureDetail(noteDetail);
//        iNoteDetailRepository.updateNoteDetail(noteDetail);
    }

    /**
     * 删除笔记
     *
     * @param noteId
     * @throws Exception
     */
    @Override
    public void deleteNote(String noteId) throws Exception {

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
        NoteDetail noteDetail = iNoteDetailRepository.getNoteDetail(noteId);
        if (noteDetail != null) {
            noteInfo.setDetail(noteDetail.getDetail());
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
        qIn.put("pageSize", pageSize);
        List noteList = noteInfoMapper.listNoteByUserId(qIn);
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
        Integer total = noteInfoMapper.totalNoteByCategoryId(categoryId);
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
        qIn.put("pageSize", pageSize);
        List noteList = noteInfoMapper.listNoteByCategory(qIn);
        return noteList;
    }

    @Override
    public void fractureDetail(NoteDetail noteDetail) throws Exception {
        for(int i=0;i<noteDetail.getDetail().length();i++){
            /**
             * 打碎，加密，排序，保存
             * 读取一个字节，进行hash256
             */
        }
    }
}
