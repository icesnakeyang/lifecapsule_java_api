package com.gogoyang.lifecapsule.meta.email.service;

import com.gogoyang.lifecapsule.meta.email.entity.RecipientEmail;

import java.util.ArrayList;

public interface IRecipientEmailService {
    /**
     * 添加一个新的接收人email
     * @param email
     * @throws Exception
     */
    void addEmail(RecipientEmail email) throws Exception;

    /**
     * 根据接收人id（recipientId）查询接收人所有登记的email
     * @param recipientId
     * @return
     * @throws Exception
     */
    ArrayList<RecipientEmail> listRecipientEmailByRecipientId(String recipientId) throws Exception;
}
