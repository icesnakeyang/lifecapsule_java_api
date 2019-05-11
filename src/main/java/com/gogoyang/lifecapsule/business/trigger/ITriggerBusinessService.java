package com.gogoyang.lifecapsule.business.trigger;

import java.util.Map;

public interface ITriggerBusinessService {
    /**
     * 创建一个新的笔记接收人
     * @param in
     * @return
     * @throws Exception
     */
    Map createRecipient(Map in) throws Exception;

    /**
     * 根据笔记id查询所有的触发器
     * @param in
     * @return
     * @throws Exception
     */
    Map listTriggerByNoteId(Map in) throws Exception;

    Map listRecipientByTriggerId(Map in) throws Exception;

    Map getTriggerByTriggerId(Map in) throws Exception;

    Map getRecipientByRecipientId(Map in) throws Exception;
}
