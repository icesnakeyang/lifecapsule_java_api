package com.gogoyang.lifecapsule.meta.gogoKey.dao.repository;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GogoKeyRepository implements IGogoKeyRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public GogoKeyRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createGogoKey(GogoKey gogoKey) throws Exception {
        mongoTemplate.save(gogoKey);
    }

    @Override
    public void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception {
        mongoTemplate.save(gogoPublicKey);
    }

    @Override
    public List<GogoPublicKey> listGogoPublicKey() throws Exception {
        Query query = new Query(Criteria.where("status").is("active"));
        return mongoTemplate.find(query, GogoPublicKey.class);
    }

    @Override
    public GogoPublicKey getGogoPublicKey(String gogoPublicKeyId) throws Exception {
        Query query = new Query(Criteria.where("gogoPublicKeyId").is(gogoPublicKeyId));
        return mongoTemplate.findOne(query, GogoPublicKey.class);
    }

    @Override
    public void deleteGogoPublicKey(String uuid) throws Exception {
        Query query = new Query(Criteria.where("gogoPublicKeyId").is(uuid));
        mongoTemplate.findAndRemove(query, GogoPublicKey.class);
    }

    @Override
    public void updateGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception {
        if (gogoPublicKey.get_id() == null) {
            return;
        }
        mongoTemplate.save(gogoPublicKey);
    }

    @Override
    public GogoKey getGogoKey(String gogoKeyId) throws Exception {
        Query query = new Query(Criteria.where("gogoKeyId").is(gogoKeyId));
        return mongoTemplate.findOne(query, GogoKey.class);
    }

    @Override
    public GogoKey getGogoKeyByTriggerId(String triggerId) throws Exception {
        Query query = new Query(Criteria.where("triggerId").is(triggerId));
        return mongoTemplate.findOne(query, GogoKey.class);
    }
}
