package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.dao.IRecipientRepository;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipientService implements IRecipientService {
    private final IRecipientRepository iRecipientRepository;

    @Autowired
    public RecipientService(IRecipientRepository iRecipientRepository) {
        this.iRecipientRepository = iRecipientRepository;
    }

    /**
     * 保存一个接收人
     * 如果有_id值，更新，没有就新增
     * @param recipient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRecipient(Recipient recipient) throws Exception {
        iRecipientRepository.saveRecipient(recipient);
    }

    /**
     * 根据触发器id查询所有接收人
     * @param triggerId
     * @return
     * @throws Exception
     */
    @Override
    public List<Recipient> listRecipientByTriggerId(String triggerId) throws Exception {
        List<Recipient> recipientList = iRecipientRepository.listRecipientByTriggerId(triggerId);
        return recipientList;
    }

    /**
     * 根据接收人id，查询接收人信息
     * @param recipientId
     * @return
     * @throws Exception
     */
    @Override
    public Recipient getRecipientByRecipientId(String recipientId) throws Exception {
        Recipient recipient=iRecipientRepository.getRecipientByRecipientId(recipientId);
        return recipient;
    }
}
