package com.gogoyang.lifecapsule.business.user.profile;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileBusinessService implements IUserProfileBusinessService {
    private final ICommonService iCommonService;
    private final IUserInfoService iUserInfoService;

    public UserProfileBusinessService(IUserInfoService iUserInfoService,
                                      ICommonService iCommonService) {
        this.iUserInfoService = iUserInfoService;
        this.iCommonService = iCommonService;
    }

    @Override
    public void saveNickname(Map in) throws Exception {
        String token = in.get("token").toString();
        String nickname = in.get("nickname").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);
        userInfo.setNickname(nickname);
        iUserInfoService.updateNickname(userInfo);
    }

    @Override
    public void savePassword(Map in) throws Exception {
        String token = in.get("token").toString();
        String password = in.get("password").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);
        password = GogoTools.encoderBySHA256(password);
        password = GogoTools.encoderByMd5(password);
        userInfo.setPassword(password);
        iUserInfoService.updateUserPassword(userInfo.getPassword(), userInfo.getUserId());
    }

    /**
     * 绑定手机号码
     *
     * @param in
     * @throws Exception
     */
    @Override
    public void bindPhone1(Map in) throws Exception {
        String token = in.get("token").toString();
        String phone = in.get("phone").toString();

        UserInfo currentUser = iCommonService.getUserByToken(token);
        UserInfo bindUser = iUserInfoService.getUserByPhone(phone);
        if (bindUser != null) {
            if (!bindUser.getUserId().equals(currentUser.getUserId())) {
                throw new Exception("10027");
            }
        }

        /**
         * 手机号码有效，可以绑定，发送验证码
         */
        //临时代码，先直接把phone写入userinfo表
        bindUser = new UserInfo();
        bindUser.setPhone(phone);
        bindUser.setUserId(currentUser.getUserId());
        iUserInfoService.updateUserPhone(bindUser);
    }

    /**
     * 绑定手机验证码
     *
     * @param in
     * @throws Exception
     */
    @Override
    public void bindPhone2(Map in) throws Exception {

    }

    /**
     * 绑定Email
     *
     * @param in
     * @throws Exception
     */
    @Override
    public void bindEmail1(Map in) throws Exception {
        String token = in.get("token").toString();
        String email = in.get("email").toString();

        UserInfo currentUser = iCommonService.getUserByToken(token);
        UserInfo bindUser = iUserInfoService.getUserByEmail(email);

        if (bindUser != null) {
            if (!bindUser.getUserId().equals(currentUser.getUserId())) {
                throw new Exception("10028");
            }
        }

        bindUser = new UserInfo();
        bindUser.setUserId(currentUser.getUserId());
        bindUser.setEmail(email);
        iUserInfoService.updateUserEmail(bindUser);
    }

    @Override
    public Map getUserByToken(Map in) throws Exception {
        String token = in.get("token").toString();
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        Map out = new HashMap();
        out.put("userInfo", userInfo);
        return out;
    }
}
