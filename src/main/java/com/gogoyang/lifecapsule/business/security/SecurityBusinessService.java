package com.gogoyang.lifecapsule.business.security;

import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import com.gogoyang.lifecapsule.meta.security.service.ISecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class SecurityBusinessService implements ISecurityBusinessService {
    private final ISecurityService iSecurityService;

    public SecurityBusinessService(ISecurityService iSecurityService) {
        this.iSecurityService = iSecurityService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRSAKey(Map in) throws Exception {
        String privateKey = in.get("privateKey").toString();
        String keyToken = in.get("keyToken").toString();

        SecurityKey key=new SecurityKey();
        key.setKeyToken(keyToken);
        key.setPrivateRSA(privateKey);

        iSecurityService.saveRSAKey(key);

    }
}
