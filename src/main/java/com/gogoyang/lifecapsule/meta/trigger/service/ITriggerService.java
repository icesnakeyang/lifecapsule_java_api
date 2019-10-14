package com.gogoyang.lifecapsule.meta.trigger.service;

import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;

import java.util.ArrayList;
import java.util.Map;

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

    void updateTriggerRemark(Trigger trigger) throws Exception;

    /**
     * 根据noteId查询trigger
     * @param noteId
     * @return
     * @throws Exception
     */
    Trigger getTriggerByNoteId(String noteId) throws Exception;

    void deleteTrigger(String triggerId) throws Exception;
}
