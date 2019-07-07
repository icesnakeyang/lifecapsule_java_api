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
    public GogoPublicKey getGogoPublicKey(String uuid) throws Exception {
        Query query = new Query(Criteria.where("uuid").is(uuid));
        return mongoTemplate.findOne(query, GogoPublicKey.class);
    }
}
