package com.gogoyang.lifecapsule.meta.recipient.dao;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.entity.RecipientPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RecipientRepository implements IRecipientRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RecipientRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveRecipient(Recipient recipient) throws Exception {
        mongoTemplate.save(recipient);
    }

    @Override
    public List<Recipient> listRecipientByNoteId(String noteId) throws Exception {
        Query query = new Query(Criteria.where("noteId").is(noteId));
        List<Recipient> outList = mongoTemplate.find(query, Recipient.class);
        return outList;
    }

    @Override
    public List<RecipientPerson> listRecipientPerson(String recipientId) {
//        public List<Map> listUser(String key, String value) {
        Query query = new Query(Criteria.where("recipientId").is(recipientId));
//        query.addCriteria(Criteria.where(condition.getKey()).regex(".*?\\" +condition.getValue().toString()+ ".*"));
        List<RecipientPerson> outList = mongoTemplate.find(query, RecipientPerson.class);
        return outList;
    }

    @Override
    public Recipient getRecipientByRecipientId(String recipientId) throws Exception {
        Query query = new Query(Criteria.where("recipientId").is(recipientId));
        Recipient recipient = mongoTemplate.findOne(query, Recipient.class);
        return recipient;
    }

    @Override
    public void saveRecipientPerson(RecipientPerson recipientPerson) throws Exception {
        mongoTemplate.save(recipientPerson);
    }

    @Override
    public RecipientPerson getPersonByPersonId(String personId) throws Exception {
        Query query = new Query(Criteria.where("personId").is(personId));
        RecipientPerson person = mongoTemplate.findOne(query, RecipientPerson.class);
        return person;
    }
}

