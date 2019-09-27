package com.gogoyang.lifecapsule.meta.user.service;

import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;

public interface IUserInfoService {
    /**
     * 创建一个用户
     *
     * @param userInfo
     * @throws Exception
     */
    void createUser(UserInfo userInfo) throws Exception;

    /**
     * 修改用户密码
     *
     * @param password
     * @param userId
     * @throws Exception
     */
    void updateUserPassword(String password, String userId) throws Exception;

    void deleteUser(UserInfo userInfo) throws Exception;

    UserInfo getUserByUserId(String userId) throws Exception;

    /**
     * 根据用户token查询用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    UserInfo getUserByUserToken(String token) throws Exception;

    /**
     * 根据用户手机号码和登录密码查询用户
     *
     * @param phone
     * @param password
     * @return
     * @throws Exception
     */
    UserInfo getUserByPhonePassword(String phone, String password) throws Exception;

    /**
     * 查询user_info表里是否有该手机号码的用户
     *
     * @param phone
     * @return
     * @throws Exception
     */
    UserInfo getUserByPhone(String phone) throws Exception;

    /**
     * 根据用户email和登录密码查询用户
     *
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    UserInfo getUserByEmailPassword(String email, String password) throws Exception;

    /**
     * 查询user_info表里是否有已使用该email的用户
     *
     * @param email
     * @return
     * @throws Exception
     */
    UserInfo getUserByEmail(String email) throws Exception;

    /**
     * 更新token
     * @param userInfo
     * @throws Exception
     */
    void updateUserToken(UserInfo userInfo) throws Exception;


    void updateNickname(UserInfo userInfo) throws Exception;
}
