package com.gogoyang.lifecapsule.meta.condition.service;

import com.gogoyang.lifecapsule.meta.condition.dao.repository.IConditionRepository;
import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConditionService implements IConditionService {
    private final IConditionRepository iConditionRepository;

    @Autowired
    public ConditionService(IConditionRepository iConditionRepository) {
        this.iConditionRepository = iConditionRepository;
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
        iConditionRepository.createCondition(condition);
    }

    /**
     * 根据触发器id，triggerId查询所有的触发条件condition
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    @Override
    public List<Condition> listConditionByTriggerId(String triggerId) throws Exception {
        List<Condition> conditionList = iConditionRepository.listConditionByTriggerId(triggerId);
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
        Condition condition=iConditionRepository.getConditionByConditionId(conditionId);
        return condition;
    }
}
