package com.gogoyang.lifecapsule.meta.security.dao;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;


public interface ISecurityRepository {
    void saveRSA(SecurityKey key) throws Exception;
    SecurityKey getRSAPrivateKey(String keyToken) throws Exception;
    void deleteRSAKey(String keyToken) throws Exception;
}
