package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.entity.RecipientPerson;

import java.util.List;
import java.util.Map;

public interface IRecipientService {
    void saveRecipient(Recipient recipient) throws Exception;

    void saveRecipientPerson(RecipientPerson recipientPerson) throws Exception;

    /**
     * 根据noteId，查询所有的触发条件和接收人
     *
     * @param noteId
     * @return
     * @throws Exception
     */
    List<Recipient> listRecipientByNoteId(String noteId) throws Exception;

    /**
     * 根据recipientId查询所有接收人
     *
     * @param recipientId
     * @return
     * @throws Exception
     */
    Map listRecipientPersonByRecipientId(String recipientId) throws Exception;

    Recipient getRecipientByRecipientId(String recipientId) throws Exception;

    RecipientPerson getPersonByPersonId(String personId) throws Exception;
}
