package com.gogoyang.lifecapsule.meta.security.dao;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecurityKeyMapper {
    void saveRSA(SecurityKey key);

    SecurityKey getRSAPrivateKey(String keyToken);

    void deleteRSAKey(String keyToken);
}
