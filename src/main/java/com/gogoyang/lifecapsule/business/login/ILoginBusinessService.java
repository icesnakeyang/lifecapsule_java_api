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
}
