package com.gogoyang.lifecapsule.business.trigger;

import java.util.Map;

public interface ITriggerBusinessService {
    /**
     * 接收人 recipient
     */
    /**
     * 创建一个新的笔记接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map createRecipient(Map in) throws Exception;
    /**
     * 修改一个接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map updateRecipient(Map in) throws Exception;
    /**
     * 删除一个接收人
     *
     * @param in
     * @throws Exception
     */
    void deleteRecipient(Map in) throws Exception;
    /**
     * 根据接收人Id读取接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getRecipientByRecipientId(Map in) throws Exception;
    /**
     * 根据触发器Id读取所有接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listRecipientByTriggerId(Map in) throws Exception;


    /**
     * 触发条件 gogoKey
     */
    /**
     * 保存gogoKey
     *
     * @param in
     * @throws Exception
     */
    void saveGogoKey(Map in) throws Exception;
    /**
     * 根据triggerId读取gogoKey
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getGogoKeyByTriggerId(Map in) throws Exception;


    /**
     * 触发器 Trigger
     */
    /**
     * 根据笔记id查询所有的触发器
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listTriggerByNoteId(Map in) throws Exception;
    /**
     * 读取一个触发器详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getTriggerByTriggerId(Map in) throws Exception;
    /**
     * 根据笔记Id读取触发器详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map getTriggerByNoteId(Map in) throws Exception;

    void deleteTrigger(Map in) throws Exception;

    void saveTrigger(Map in) throws Exception;

    /**
     * 保存trigger的remark
     * @param in
     * @throws Exception
     */
    void saveTriggerRemark(Map in) throws Exception;
}
