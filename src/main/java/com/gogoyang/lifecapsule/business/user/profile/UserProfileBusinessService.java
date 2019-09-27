package com.gogoyang.lifecapsule.business.user.profile;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProfileBusinessService implements IUserProfileBusinessService{
    private final ICommonService iCommonService;
    private final IUserInfoService iUserInfoService;

    public UserProfileBusinessService(IUserInfoService iUserInfoService,
                                      ICommonService iCommonService) {
        this.iUserInfoService = iUserInfoService;
        this.iCommonService = iCommonService;
    }

    @Override
    public void saveNickname(Map in) throws Exception {
        String token=in.get("token").toString();
        String nickname=in.get("nickname").toString();

        UserInfo userInfo=iCommonService.getUserByToken(token);
        userInfo.setNickname(nickname);
        iUserInfoService.updateNickname(userInfo);
    }
}
