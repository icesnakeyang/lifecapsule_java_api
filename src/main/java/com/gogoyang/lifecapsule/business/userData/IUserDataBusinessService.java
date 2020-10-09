package com.gogoyang.lifecapsule.business.userData;

import java.util.Map;

public interface IUserDataBusinessService {
    /**
     * 把笔记转成Api服务
     * @param in
     * @return
     * @throws Exception
     */
    Map convertToApi(Map in) throws Exception;

    Map getNoteApi(Map in) throws Exception;

    Map getUserDataApi(Map in) throws Exception;

    Map getUserDataApi2(Map in) throws Exception;
}
