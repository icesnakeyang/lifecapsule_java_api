package com.gogoyang.lifecapsule.meta.trigger.dao;

import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TriggerMapper {
    /**
     * 创建一个触发器 trigger
     * @param trigger
     */
    void createTrigger(Trigger trigger);

    /**
     * 根据触发器id，查询触发器
     * @param triggerId
     * @return
     */
    Trigger getTriggerByTriggerId(String triggerId);
}
