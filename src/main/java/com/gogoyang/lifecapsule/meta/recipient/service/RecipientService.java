package com.gogoyang.lifecapsule.meta.recipient.service;

import com.gogoyang.lifecapsule.meta.recipient.dao.IRecipientRepository;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
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

    @Override
    public List<Recipient> listRecipientByNoteId(String noteId) throws Exception {
        List<Recipient> recipientList=iRecipientRepository.listRecipientByNoteId(noteId);
        return recipientList;
    }

    @Override
    public Map listRecipientPersonByRecipientId(String recipientId) throws Exception {
        List list=iRecipientRepository.listRecipientPerson(recipientId);
        Map out=new HashMap();
        out.put("list", list);
        return out;
    }
}
