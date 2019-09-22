package com.gogoyang.lifecapsule.business.common;

import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import org.springframework.stereotype.Service;

@Service
public class CommonService implements ICommonService {
    private final IUserInfoService iUserInfoService;

    public CommonService(IUserInfoService iUserInfoService) {
        this.iUserInfoService = iUserInfoService;
    }

    @Override
    public UserInfo getUserByToken(String token) throws Exception {
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }
        return userInfo;
    }
}
