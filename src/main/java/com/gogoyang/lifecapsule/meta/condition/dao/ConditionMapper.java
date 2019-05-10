package com.gogoyang.lifecapsule.meta.condition.dao;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConditionMapper {
    /**
     * 创建一个触发条件condition
     * @param condition
     */
    void createCondition(Condition condition);
}
