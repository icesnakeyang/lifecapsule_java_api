package com.gogoyang.lifecapsule.business.recipient;

import java.util.Map;

public interface IRecipientBusinessService {
    Map createRecipientPerson(Map in) throws Exception;

    Map listRecipientPersonByRecipientId(Map in) throws Exception;

    Map listRecipientByNoteId(Map in) throws Exception;

    /**
     * read recipient config by recipient id
     * @param in
     * @return
     * @throws Exception
     */
    Map getRecipientByRecipientId(Map in) throws Exception;

    Map getPersonByPersonId(Map in) throws Exception;
}
