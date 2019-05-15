package com.gogoyang.lifecapsule.meta.condition.service;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;

import java.util.List;

public interface IConditionService {
    /**
     * 创建一个触发条件condition
     *
     * @param condition
     * @throws Exception
     */
    void createCondition(Condition condition) throws Exception;

    /**
     * 根据触发器id，triggerId查询所有的触发条件condition
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    List<Condition> listConditionByTriggerId(String triggerId) throws Exception;

    /**
     * 根据conditionId查询触发条件信息
     * @param conditionId
     * @return
     * @throws Exception
     */
    Condition getConditionByConditionId(String conditionId) throws Exception;
}
