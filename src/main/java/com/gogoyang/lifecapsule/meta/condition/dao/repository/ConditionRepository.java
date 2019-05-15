package com.gogoyang.lifecapsule.meta.condition.dao.repository;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ConditionRepository implements IConditionRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConditionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Condition createCondition(Condition condition) throws Exception {
        Condition out = mongoTemplate.save(condition);
        return out;
    }

    @Override
    public Condition getConditionByConditionId(String conditionId) throws Exception {
        Query query = new Query(Criteria.where("conditionId").is(conditionId));
        Condition condition = mongoTemplate.findOne(query, Condition.class);
        return condition;
    }

    @Override
    public List<Condition> listConditionByTriggerId(String triggerId) throws Exception {
        Query query=new Query(Criteria.where("triggerId").is(triggerId));
        List<Condition> conditionList=mongoTemplate.find(query, Condition.class);
        return conditionList;
    }
}
