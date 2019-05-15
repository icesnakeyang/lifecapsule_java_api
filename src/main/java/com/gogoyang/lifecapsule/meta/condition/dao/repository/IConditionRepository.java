package com.gogoyang.lifecapsule.meta.condition.dao.repository;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;

import java.util.ArrayList;
import java.util.List;

public interface IConditionRepository {
    Condition createCondition(Condition condition) throws Exception;

    Condition getConditionByConditionId(String conditionId) throws Exception;

    List<Condition> listConditionByTriggerId(String triggerId) throws Exception;
}
