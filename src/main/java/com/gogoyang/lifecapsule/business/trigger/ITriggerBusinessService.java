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

    void saveCondition(Map in) throws Exception;

    /**
     * 根据conditionId查询触发条件信息
     * @param in
     * @return
     * @throws Exception
     */
    Map getConditionByConditionId(Map in) throws Exception;

    Map updateRecipient(Map in) throws Exception;

    /**
     * 删除一个接收人
     * @param in
     * @throws Exception
     */
    void deleteRecipient(Map in) throws Exception;
}
