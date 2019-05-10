package com.gogoyang.lifecapsule.meta.trigger.service;

import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;

public interface ITriggerService {
    /**
     * 创建一个触发器 trigger
     * @param trigger
     * @throws Exception
     */
    void createTrigger(Trigger trigger) throws Exception;

    /**
     * getTriggerByTriggerId
     * @param triggerId
     * @return
     * @throws Exception
     */
    Trigger getTriggerByTriggerId(String triggerId) throws Exception;
}
