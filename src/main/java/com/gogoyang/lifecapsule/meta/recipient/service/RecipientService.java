package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.email.service.IRecipientEmailService;
import com.gogoyang.lifecapsule.meta.recipient.dao.IRecipientRepository;
import com.gogoyang.lifecapsule.meta.recipient.dao.RecipientMapper;
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
    private final RecipientMapper recipientMapper;
    private final IRecipientEmailService iRecipientEmailService;

    public RecipientService(RecipientMapper recipientMapper,
                            IRecipientEmailService iRecipientEmailService) {
        this.recipientMapper = recipientMapper;
        this.iRecipientEmailService = iRecipientEmailService;
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
}
