package com.gogoyang.lifecapsule.meta.email.service;

import com.gogoyang.lifecapsule.meta.email.dao.RecipientEmailMapper;
import com.gogoyang.lifecapsule.meta.email.entity.RecipientEmail;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipientEmailService implements IRecipientEmailService {
    private final RecipientEmailMapper recipientEmailMapper;

    public RecipientEmailService(RecipientEmailMapper recipientEmailMapper) {
        this.recipientEmailMapper = recipientEmailMapper;
    }

    /**
     * 添加一个新的接收人email
     *
     * @param email
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addEmail(RecipientEmail email) throws Exception {
        recipientEmailMapper.addEmail(email);
    }

    /**
     * 根据接收人id（recipientId）查询接收人所有登记的email
     *
     * @param recipientId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<RecipientEmail> listRecipientEmailByRecipientId(String recipientId) throws Exception {
        ArrayList<RecipientEmail> recipientEmailList = recipientEmailMapper.listRecipientEmailByRecipientId(recipientId);
        return recipientEmailList;
    }
}
