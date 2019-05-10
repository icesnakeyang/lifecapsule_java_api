package com.gogoyang.lifecapsule.business.login;

import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginBusinessService implements ILoginBusinessService {
    private final IUserInfoService iUserInfoService;

    @Autowired
    public LoginBusinessService(IUserInfoService iUserInfoService) {
        this.iUserInfoService = iUserInfoService;
    }

    /**
     * 用户登录
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map loginUser(Map in) throws Exception {
        String password = in.get("password").toString();
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");

        /**
         * 如果用户没有输入密码，则抛出错误
         */
        if (password == null) {
            throw new Exception("10002");
        }

        //用户输入的密码必须进行md5加密，然后和数据库比对
        password = GogoTools.encoderByMd5(password);
        UserInfo userInfo = null;
        if (phone != null) {
            userInfo = iUserInfoService.getUserByPhonePassword(phone, password);
        }
        if (email != null) {
            userInfo = iUserInfoService.getUserByEmailPassword(email, password);
        }
        if (userInfo == null) {
            throw new Exception("10002");
        }

        Map out = new HashMap();
        out.put("user", userInfo);
        return out;
    }
}
