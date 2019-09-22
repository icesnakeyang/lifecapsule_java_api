package com.gogoyang.lifecapsule.business.common;

import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;

public interface ICommonService {
    UserInfo getUserByToken(String token) throws Exception;
}
