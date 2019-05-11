package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;

import java.util.List;
import java.util.Map;

public interface IRecipientService {
    /**
     * 保存接收人
     *
     * @param recipient
     * @throws Exception
     */
    void saveRecipient(Recipient recipient) throws Exception;

    /**
     * 根据触发器id，即triggerId查询所有接收人
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    List<Recipient> listRecipientByTriggerId(String triggerId) throws Exception;

    /**
     * 根据接收人id，查询接收人信息
     *
     * @param recipientId
     * @return
     * @throws Exception
     */
    Recipient getRecipientByRecipientId(String recipientId) throws Exception;
}
