package com.gogoyang.lifecapsule.meta.user.service;

import com.gogoyang.lifecapsule.meta.user.dao.UserInfoDao;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoService implements IUserInfoService {
    private final UserInfoDao userInfoDao;

    @Autowired
    public UserInfoService(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    /**
     * 创建一个用户
     *
     * @param userInfo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUser(UserInfo userInfo) throws Exception {
        userInfoDao.createUser(userInfo);
    }

    /**
     * 修改用户密码
     *
     * @param password
     * @param userId
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserPassword(String password, String userId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("userId", userId);
        qIn.put("password", password);
        userInfoDao.updateUserPassword(qIn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(UserInfo userInfo) throws Exception {

    }

    @Override
    public UserInfo getUserByUserId(String userId) throws Exception {
        UserInfo userInfo = userInfoDao.getUserByUserId(userId);
        return userInfo;
    }

    /**
     * 根据用户token查询用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo getUserByUserToken(String token) throws Exception {
        UserInfo userInfo = userInfoDao.getUserByUserToken(token);
        return userInfo;
    }

    /**
     * 根据用户手机号码和登录密码查询用户
     *
     * @param phone
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo getUserByPhonePassword(String phone, String password) throws Exception {
        Map qIn = new HashMap();
        qIn.put("phone", phone);
        qIn.put("password", password);
        UserInfo userInfo = userInfoDao.getUserByPhonePassword(qIn);
        return userInfo;
    }

    /**
     * 查询user_info表里是否有该手机号码的用户
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo getUserByPhone(String phone) throws Exception {
        UserInfo userInfo = userInfoDao.getUserByPhone(phone);
        return userInfo;
    }

    /**
     * 根据用户email和登录密码查询用户
     *
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo getUserByEmailPassword(String email, String password) throws Exception {
        Map qIn = new HashMap();
        qIn.put("email", email);
        qIn.put("password", password);
        UserInfo userInfo = userInfoDao.getUserByEmailPassword(qIn);
        return userInfo;
    }

    /**
     * 查询user_info表里是否有已使用该email的用户
     *
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo getUserByEmail(String email) throws Exception {
        UserInfo userInfo = userInfoDao.getUserByEmail(email);
        return userInfo;
    }

    /**
     * 更新用户token
     *
     * @param userInfo
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserToken(UserInfo userInfo) throws Exception {
        userInfoDao.updateUserToken(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNickname(UserInfo userInfo) throws Exception {
        userInfoDao.updateNickname(userInfo);
    }

    @Override
    public void updateUserPhone(UserInfo bindUser) throws Exception {
        userInfoDao.updateUserPhone(bindUser);
    }

    @Override
    public void updateUserEmail(UserInfo bindUser) throws Exception {
        userInfoDao.updateUserEmail(bindUser);
    }
}
