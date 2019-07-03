package com.gogoyang.lifecapsule.meta.security.service;

import com.gogoyang.lifecapsule.meta.security.dao.SecurityRepository;
import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService implements ISecurityService {
    private final SecurityRepository securityRepository;

    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRSAKey(SecurityKey key) throws Exception {
        securityRepository.saveRSA(key);
    }

    @Override
    public String getRSAKey(String keyToken) throws Exception {
        SecurityKey privateKey=securityRepository.getRSAPrivateKey(keyToken);
        return privateKey.getPrivateRSA();
    }

    @Override
    public void deleteRSAKey(String keyToken) throws Exception {
        securityRepository.deleteRSAKey(keyToken);
    }
}
