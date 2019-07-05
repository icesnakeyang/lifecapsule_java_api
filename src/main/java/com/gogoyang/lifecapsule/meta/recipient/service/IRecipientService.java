package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;

import java.util.ArrayList;
import java.util.List;

public interface IRecipientService {
    /**
     * 创建一个接收人
     *
     * @param recipient
     * @throws Exception
     */
    void createRecipient(Recipient recipient) throws Exception;

    /**
     * 根据触发器id，即triggerId查询所有接收人
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    ArrayList<Recipient> listRecipientByTriggerId(String triggerId) throws Exception;

    /**
     * 根据接收人id，查询接收人信息
     *
     * @param recipientId
     * @return
     * @throws Exception
     */
    Recipient getRecipientByRecipientId(String recipientId) throws Exception;

    /**
     * 修改接收人信息
     * @param recipient
     * @throws Exception
     */
    void updateRecipient(Recipient recipient) throws Exception;

    /**
     * 删除一个接收人
     * @param recipientId
     * @throws Exception
     */
    void deleteRecipient(String recipientId) throws Exception;
}
