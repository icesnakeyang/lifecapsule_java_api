package com.gogoyang.lifecapsule.meta.recipient.dao;

import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.entity.RecipientPerson;

import java.util.List;

public interface IRecipientRepository {
    void saveRecipient(Recipient recipient) throws Exception;

    List<Recipient> listRecipientByNoteId(String noteId) throws Exception;

    List<RecipientPerson> listRecipientPerson(String recipientId) throws Exception;

    Recipient getRecipientByRecipientId(String recipientId) throws Exception;

    void saveRecipientPerson(RecipientPerson recipientPerson) throws Exception;

    RecipientPerson getPersonByPersonId(String personId) throws Exception;
}
