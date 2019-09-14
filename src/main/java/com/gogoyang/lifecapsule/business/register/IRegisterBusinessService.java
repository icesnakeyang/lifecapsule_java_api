package com.gogoyang.lifecapsule.business.register;

import java.util.Map;

public interface IRegisterBusinessService {
    /**
     * 用户注册
     * @param in
     * @return
     * @throws Exception
     */
    Map registerMe(Map in) throws Exception;

    Map createBlankUser() throws Exception;
}
