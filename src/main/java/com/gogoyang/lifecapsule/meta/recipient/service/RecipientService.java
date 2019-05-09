package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.dao.IRecipientRepository;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.entity.RecipientPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRecipient(Recipient recipient) throws Exception {
        iRecipientRepository.saveRecipient(recipient);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRecipientPerson(RecipientPerson recipientPerson) throws Exception {
        iRecipientRepository.saveRecipientPerson(recipientPerson);
    }

    @Override
    public List<Recipient> listRecipientByNoteId(String noteId) throws Exception {
        List<Recipient> recipientList = iRecipientRepository.listRecipientByNoteId(noteId);
        return recipientList;
    }

    @Override
    public Map listRecipientPersonByRecipientId(String recipientId) throws Exception {
        List<RecipientPerson> personList = iRecipientRepository.listRecipientPerson(recipientId);
        Map out = new HashMap();
        out.put("personList", personList);
        return out;
    }

    @Override
    public Recipient getRecipientByRecipientId(String recipientId) throws Exception {
        Recipient recipient = iRecipientRepository.getRecipientByRecipientId(recipientId);
        return recipient;

    }

    @Override
    public RecipientPerson getPersonByPersonId(String personId) throws Exception {
        RecipientPerson person=iRecipientRepository.getPersonByPersonId(personId);
        return person;
    }
}
