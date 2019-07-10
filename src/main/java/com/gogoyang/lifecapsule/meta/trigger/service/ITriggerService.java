package com.gogoyang.lifecapsule.meta.trigger.service;

import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;

import java.util.ArrayList;

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

    /**
     * 根据笔记id查询所有的触发器
     * @param noteId
     * @return
     * @throws Exception
     */
    ArrayList<Trigger> listTriggerByNoteId(String noteId) throws Exception;

    /**
     * 修改触发器的名称和说明
     * @param trigger
     * @throws Exception
     */
    void updateTrigger(Trigger trigger) throws Exception;
}
