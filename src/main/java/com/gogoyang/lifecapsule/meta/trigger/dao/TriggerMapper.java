package com.gogoyang.lifecapsule.meta.trigger.dao;

import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据笔记id查询所有的触发器
     * @param qIn
     */
    ArrayList<Trigger> listTriggerByNoteId(Map qIn);

    /**
     * 修改触发器的名称和说明
     * @param trigger
     */
    void updateTrigger(Trigger trigger);

    /**
     * 根据noteId查询trigger
     * @param noteId
     * @return
     */
    Trigger getTriggerByNoteId(String noteId);

    void deleteTrigger(String triggerId);
}
