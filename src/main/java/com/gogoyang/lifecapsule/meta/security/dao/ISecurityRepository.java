package com.gogoyang.lifecapsule.meta.security.dao;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;


public interface ISecurityRepository {
    void saveRSA(SecurityKey key) throws Exception;
}
