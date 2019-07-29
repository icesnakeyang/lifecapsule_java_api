package com.gogoyang.lifecapsule.meta.security.service;

import com.gogoyang.lifecapsule.meta.security.dao.SecurityKeyMapper;
import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService implements ISecurityService {
    private final SecurityKeyMapper securityKeyMapper;

    public SecurityService(SecurityKeyMapper securityKeyMapper) {
        this.securityKeyMapper = securityKeyMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRSAKey(SecurityKey key) throws Exception {
        securityKeyMapper.saveRSA(key);
    }

    @Override
    public String getRSAKey(String keyToken) throws Exception {
        SecurityKey privateKey=securityKeyMapper.getRSAPrivateKey(keyToken);
        return privateKey.getPrivateRSA();
    }

    @Override
    public void deleteRSAKey(String keyToken) throws Exception {
        securityKeyMapper.deleteRSAKey(keyToken);
    }
}
