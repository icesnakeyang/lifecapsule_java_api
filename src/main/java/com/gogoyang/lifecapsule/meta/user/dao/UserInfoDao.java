package com.gogoyang.lifecapsule.meta.user.dao;

import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface UserInfoDao {
    void createUser(UserInfo userInfo);

    void updateUserPassword(Map qIn);

    /**
     * 根据用户手机号码和登录密码查询用户
     * @param qIn
     * @return
     */
    UserInfo getUserByPhonePassword(Map qIn);

    /**
     * 根据用户email和登录密码查询用户
     * @param qIn
     * @return
     */
    UserInfo getUserByEmailPassword(Map qIn);

    /**
     * 查询user_info表里是否有该手机号码的用户
     * @param phone
     * @return
     */
    UserInfo getUserByPhone(String phone);

    /**
     * 查询user_info表里是否有已使用该email的用户
     * @param email
     * @return
     */
    UserInfo getUserByEmail(String email);

    /**
     * 根据用户token查询用户信息
     * @param token
     * @return
     */
    UserInfo getUserByUserToken(String token);

    UserInfo getUserByUserId(String userId);

    /**
     * 更新用户token
     * @param userInfo
     */
    void updateUserToken(UserInfo userInfo);
}
