package com.gogoyang.lifecapsule.meta.condition.service;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;

public interface IConditionService {
    /**
     * 创建一个触发条件condition
     * @param condition
     * @throws Exception
     */
    void createCondition(Condition condition) throws Exception;
}
