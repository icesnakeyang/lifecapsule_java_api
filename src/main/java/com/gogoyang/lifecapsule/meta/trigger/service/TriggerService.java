package com.gogoyang.lifecapsule.meta.trigger.service;

import com.gogoyang.lifecapsule.meta.trigger.dao.TriggerMapper;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.normalizer.TrieIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TriggerService implements ITriggerService {
    private final TriggerMapper triggerMapper;

    public TriggerService(TriggerMapper triggerMapper) {
        this.triggerMapper = triggerMapper;
    }

    /**
     * 创建一个触发器 trigger
     *
     * @param trigger
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTrigger(Trigger trigger) throws Exception {
        triggerMapper.createTrigger(trigger);
    }

    /**
     * getTriggerByTriggerId
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    @Override
    public Trigger getTriggerByTriggerId(String triggerId) throws Exception {
        Trigger trigger = triggerMapper.getTriggerByTriggerId(triggerId);
        return trigger;
    }

    /**
     * 根据笔记id查询所有的触发器
     * @param noteId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Trigger> listTriggerByNoteId(String noteId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("noteId", noteId);
        ArrayList<Trigger> triggerList = triggerMapper.listTriggerByNoteId(qIn);
        return triggerList;
    }
}
