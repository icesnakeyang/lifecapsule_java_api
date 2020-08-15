package com.gogoyang.lifecapsule.meta.userData.service;

import com.gogoyang.lifecapsule.meta.userData.entity.UserData;

import java.util.Map;

public interface IUserDataService {
    void createUserData(UserData userData);

    /**
     * 读取userData
     * @param qIn
     * dataId
     * dataToken
     * noteId
     * @return
     */
    UserData getUserData(Map qIn);
}
