package com.gogoyang.lifecapsule.meta.userData.dao;

import com.gogoyang.lifecapsule.meta.userData.entity.UserData;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDataDao {
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
