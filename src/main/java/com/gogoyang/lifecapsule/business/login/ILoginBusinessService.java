package com.gogoyang.lifecapsule.business.login;

import java.util.Map;

public interface ILoginBusinessService {
    /**
     * 用户登录
     * @param in
     * @return
     * @throws Exception
     */
    Map loginUser(Map in) throws Exception;

    Map loginBlankUser(String token) throws Exception;

    /**
     * 重新申请一个用户token
     * @param in
     * @throws Exception
     */
    void resignUserToken(Map in) throws Exception;
}
