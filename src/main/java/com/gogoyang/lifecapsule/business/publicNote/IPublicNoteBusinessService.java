package com.gogoyang.lifecapsule.business.publicNote;

import java.util.Map;

public interface IPublicNoteBusinessService {
    void publishNote(Map in) throws Exception;

    Map listPublicNote(Map in) throws Exception;

    Map getPublicNote(Map in) throws Exception;
}
