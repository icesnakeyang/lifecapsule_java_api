package com.gogoyang.lifecapsule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppleService implements IAppleService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Tank> listApple() {
        Query query = new Query(Criteria.where("name").is("apple"));
        List<Tank> appleList = mongoTemplate.find(query, Tank.class);
        return appleList;
    }
}
