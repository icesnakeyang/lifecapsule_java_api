package com.gogoyang.lifecapsule.meta.condition.dao;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ConditionMapper {
    /**
     * 创建一个触发条件condition
     * @param condition
     */
    void createCondition(Condition condition);

    /**
     * 根据触发器id，triggerId查询所有的触发条件condition
     * @param triggerId
     */
    ArrayList<Condition> listConditionByTriggerId(String triggerId);

    /**
     * 根据conditionId查询触发条件信息
     * @param conditionId
     * @return
     */
    Condition getConditionByConditionId(String conditionId);
}
