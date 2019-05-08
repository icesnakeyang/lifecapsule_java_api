package com.gogoyang.lifecapsule.meta.recipient.dao;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;

import java.util.List;

public interface IRecipientRepository {
    void saveRecipient(Recipient recipient) throws Exception;

    List<Recipient> listRecipientByNoteId(String noteId) throws Exception;

    List<Recipient> listRecipientPerson(String recipientId);

}
