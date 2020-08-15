package com.gogoyang.lifecapsule.meta.userData.service;

import com.gogoyang.lifecapsule.meta.userData.dao.UserDataDao;
import com.gogoyang.lifecapsule.meta.userData.entity.UserData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDataService implements IUserDataService{
    private final UserDataDao userDataDao;

    public UserDataService(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }

    @Override
    public void createUserData(UserData userData) {
        userDataDao.createUserData(userData);
    }

    /**
     * 读取userData
     * @param qIn
     * dataId
     * dataToken
     * noteId
     * @return
     */
    @Override
    public UserData getUserData(Map qIn) {
        UserData userData=userDataDao.getUserData(qIn);
        return userData;
    }
}
