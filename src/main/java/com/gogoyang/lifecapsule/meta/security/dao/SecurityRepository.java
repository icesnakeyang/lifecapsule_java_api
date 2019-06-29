package com.gogoyang.lifecapsule.meta.security.dao;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class SecurityRepository implements ISecurityRepository {
    private final MongoTemplate mongoTemplate;

    public SecurityRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveRSA(SecurityKey key) throws Exception {
        mongoTemplate.save(key);
    }
}
