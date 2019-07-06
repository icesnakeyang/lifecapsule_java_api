package com.gogoyang.lifecapsule.meta.gogoKey.dao.repository;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GogoKeyRepository implements IGogoKeyRepository{
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
    public void createGogoPublicKey() throws Exception {
//        mongoTemplate.save(gogoKey);
    }
}
