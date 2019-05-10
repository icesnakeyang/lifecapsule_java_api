package com.gogoyang.lifecapsule.meta.condition.service;

import com.gogoyang.lifecapsule.meta.condition.dao.ConditionMapper;
import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
