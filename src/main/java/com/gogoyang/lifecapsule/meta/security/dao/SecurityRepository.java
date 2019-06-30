package com.gogoyang.lifecapsule.meta.security.dao;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;


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

    @Override
    public SecurityKey getRSAPrivateKey(String keyToken) throws Exception {
        Query query = new Query(Criteria.where("keyToken").is(keyToken));
        SecurityKey securityKey = mongoTemplate.findOne(query, SecurityKey.class);
        return securityKey;
    }
}
