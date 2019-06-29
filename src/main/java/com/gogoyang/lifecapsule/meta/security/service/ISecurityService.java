package com.gogoyang.lifecapsule.meta.security.service;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;

public interface ISecurityService {

    void saveRSAKey(SecurityKey key) throws Exception;
}
