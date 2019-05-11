package com.gogoyang.lifecapsule.meta.note.dao.repository;

import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NoteDetailRepository implements INoteDetailRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NoteDetailRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 创建一个笔记的详细信息
     *
     * @param noteDetail
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNoteDetail(NoteDetail noteDetail) throws Exception {
        mongoTemplate.save(noteDetail);
    }

    /**
     * 修改笔记的详细信息
     *
     * @param noteDetail
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNoteDetail(NoteDetail noteDetail) throws Exception {
        /**
         * 首先检查noteId是否存在，如果存在就是保存，否则会新增一条记录
         */
        if (noteDetail.getNoteId() == null) {
            throw new Exception("10006");
        }

        Query query = new Query(Criteria.where("noteId").is(noteDetail.getNoteId()));
        NoteDetail oldNote = mongoTemplate.findOne(query, NoteDetail.class);
        if (oldNote != null) {
            noteDetail.set_id(oldNote.get_id());
        }

        mongoTemplate.save(noteDetail);
    }

    /**
     * 删除笔记的详细信息
     *
     * @param noteId
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNoteDetail(String noteId) throws Exception {
        /**
         * 只能根据noteId来删除笔记
         */
        Query query = new Query(Criteria.where("noteId").is(noteId));
        mongoTemplate.findAndRemove(query, NoteDetail.class);
    }

    /**
     * 读取笔记的详细信息
     *
     * @param noteId
     * @throws Exception
     */
    @Override
    public NoteDetail getNoteDetail(String noteId) throws Exception {
        Query query = new Query(Criteria.where("noteId").is(noteId));
        NoteDetail noteDetail = mongoTemplate.findOne(query, NoteDetail.class);
        return noteDetail;
    }

//    public List<GogoUser> listUser(String key, String value) {
//        Query query = new Query(Criteria.where(key).regex(value));
////        query.addCriteria(Criteria.where(condition.getKey()).regex(".*?\\" +condition.getValue().toString()+ ".*"));
//        return mongoTemplate.find(query, GogoUser.class);
//    }

}
