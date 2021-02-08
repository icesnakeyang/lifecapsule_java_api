package com.gogoyang.lifecapsule.business.note.creativeNote;

import java.util.Map;

public interface ICreativeNoteBService {
    Map saveCreativeNote(Map in) throws Exception;

    Map getCreativeNote(Map in) throws Exception;
}
