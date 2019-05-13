package com.gogoyang.lifecapsule.meta.condition.service;

import com.gogoyang.lifecapsule.meta.condition.dao.ConditionMapper;
import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ConditionService implements IConditionService {
    private final ConditionMapper conditionMapper;

    public ConditionService(ConditionMapper conditionMapper) {
        this.conditionMapper = conditionMapper;
    }

    /**
     * 创建一个触发条件condition
     *
     * @param condition
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCondition(Condition condition) throws Exception {
        conditionMapper.createCondition(condition);
    }

    /**
     * 根据触发器id，triggerId查询所有的触发条件condition
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Condition> listConditionByTriggerId(String triggerId) throws Exception {
        ArrayList<Condition> conditionList = conditionMapper.listConditionByTriggerId(triggerId);
        return conditionList;
    }

    /**
     * 根据conditionId查询触发条件信息
     * @param conditionId
     * @return
     * @throws Exception
     */
    @Override
    public Condition getConditionByConditionId(String conditionId) throws Exception {
        Condition condition=conditionMapper.getConditionByConditionId(conditionId);
        return condition;
    }
}
