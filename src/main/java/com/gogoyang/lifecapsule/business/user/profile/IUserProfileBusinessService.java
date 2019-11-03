package com.gogoyang.lifecapsule.business.user.profile;

import java.util.Map;

public interface IUserProfileBusinessService {
    void saveNickname(Map in) throws Exception;

    void savePassword(Map in) throws Exception;

    void bindPhone1(Map in) throws Exception;

    void bindPhone2(Map in) throws Exception;

    void bindEmail1(Map in) throws Exception;

    Map getUserByToken(Map in) throws Exception;
}
