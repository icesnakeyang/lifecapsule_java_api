package com.gogoyang.lifecapsule.meta.recipient.dao;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;

import java.util.List;

public interface IRecipientRepository {
    /**
     * 新增一个接收人
     *
     * @param recipient
     * @throws Exception
     */
    void saveRecipient(Recipient recipient) throws Exception;

    /**
     * 获取一个触发器的所有接收人
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    List<Recipient> listRecipientByTriggerId(String noteId) throws Exception;

    /**
     * 根据接收人id，查询接收人
     *
     * @param recipientId
     * @return
     * @throws Exception
     */
    Recipient getRecipientByRecipientId(String recipientId) throws Exception;
}
