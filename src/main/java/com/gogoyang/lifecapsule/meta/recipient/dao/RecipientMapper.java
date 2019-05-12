package com.gogoyang.lifecapsule.meta.recipient.dao;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RecipientMapper {
    /**
     * 创建一个接收人
     * @param recipient
     */
    void createRecipient(Recipient recipient);

    /**
     * 根据触发器id，查询所有接收人简要信息
     * @param triggerId
     */
    ArrayList<Recipient> listRecipientByTriggerId(String triggerId);

    /**
     * 根据接收人id查询接收人
     * @param recipientId
     * @return
     */
    Recipient getRecipientByRecipientId(String recipientId);
}
