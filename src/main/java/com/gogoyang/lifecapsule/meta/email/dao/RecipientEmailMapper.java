package com.gogoyang.lifecapsule.meta.email.dao;

import com.gogoyang.lifecapsule.meta.email.entity.RecipientEmail;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface RecipientEmailMapper {
    /**
     * 添加一个新的接收人email
     * @param email
     */
    void addEmail(RecipientEmail email);

    /**
     * 根据接收人id（recipientId）查询接收人所有登记的email
     * @param recipientId
     * @return
     */
    ArrayList<RecipientEmail> listRecipientEmailByRecipientId(String recipientId);
}
