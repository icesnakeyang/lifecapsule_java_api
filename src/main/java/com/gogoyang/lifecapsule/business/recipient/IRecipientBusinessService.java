package com.gogoyang.lifecapsule.business.recipient;

import java.util.Map;

public interface IRecipientBusinessService {
    Map createRecipientPerson(Map in) throws Exception;

    Map listRecipientPerson(Map in) throws Exception;

    Map listRecipientByNoteId(Map in) throws Exception;
}
