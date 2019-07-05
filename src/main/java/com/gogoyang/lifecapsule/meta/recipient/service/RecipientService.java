package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.dao.RecipientMapper;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class RecipientService implements IRecipientService {
    private final RecipientMapper recipientMapper;

    public RecipientService(RecipientMapper recipientMapper) {
        this.recipientMapper = recipientMapper;
    }

    /**
     * 保存一个接收人
     * 如果有_id值，更新，没有就新增
     *
     * @param recipient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createRecipient(Recipient recipient) throws Exception {
        recipientMapper.createRecipient(recipient);
    }

    /**
     * 根据触发器id，查询所有接收人简要信息
     *
     * @param triggerId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Recipient> listRecipientByTriggerId(String triggerId) throws Exception {
        ArrayList<Recipient> recipientList = recipientMapper.listRecipientByTriggerId(triggerId);
        return recipientList;
    }

    /**
     * 根据接收人id查询接收人
     * @param recipientId
     * @return
     * @throws Exception
     */
    @Override
    public Recipient getRecipientByRecipientId(String recipientId) throws Exception {
        Recipient recipient=recipientMapper.getRecipientByRecipientId(recipientId);
        return recipient;
    }

    /**
     * 修改接收人信息
     * @param recipient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRecipient(Recipient recipient) throws Exception {
        recipientMapper.updateRecipient(recipient);
    }

    /**
     * 删除一个接收人
     * @param recipientId
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRecipient(String recipientId) throws Exception {
        recipientMapper.deleteRecipient(recipientId);

    }
}
