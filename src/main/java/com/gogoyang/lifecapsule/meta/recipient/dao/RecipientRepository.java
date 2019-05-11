package com.gogoyang.lifecapsule.meta.recipient.dao;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipientRepository implements IRecipientRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RecipientRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 新增一个接收人
     *
     * @param recipient
     * @throws Exception
     */
    @Override
    public void saveRecipient(Recipient recipient) throws Exception {
        mongoTemplate.save(recipient);
    }

    /**
     * 获取一个触发器的所有接收人
     *
     * @param triggerId
     * @return
     */
    @Override
    public List<Recipient> listRecipientByTriggerId(String triggerId) throws Exception{
        Query query = new Query(Criteria.where("triggerId").is(triggerId));
//        query.addCriteria(Criteria.where(condition.getKey()).regex(".*?\\" +condition.getValue().toString()+ ".*"));
        List<Recipient> outList = mongoTemplate.find(query, Recipient.class);
        return outList;
    }

    /**
     * 根据接收人id，查询接收人
     * @param recipientId
     * @return
     * @throws Exception
     */
    @Override
    public Recipient getRecipientByRecipientId(String recipientId) throws Exception {
        Query query = new Query(Criteria.where("recipientId").is(recipientId));
        Recipient recipient = mongoTemplate.findOne(query, Recipient.class);
        return recipient;
    }
}

