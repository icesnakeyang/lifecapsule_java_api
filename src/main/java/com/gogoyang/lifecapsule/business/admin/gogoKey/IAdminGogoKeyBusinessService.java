package com.gogoyang.lifecapsule.business.admin.gogoKey;

import java.util.Map;

public interface IAdminGogoKeyBusinessService {
    void createGogoPublicKey(Map in) throws Exception;

    Map listGogoPublicKey() throws Exception;

    Map getGogoPublicKey(Map in) throws Exception;
}
