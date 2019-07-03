package com.gogoyang.lifecapsule.business.register;

import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import com.gogoyang.lifecapsule.meta.category.service.ICategoryService;
import com.gogoyang.lifecapsule.meta.security.service.ISecurityService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterBusinessService implements IRegisterBusinessService {
    private final IUserInfoService iUserInfoService;
    private final ICategoryService iCategoryService;
    private final ISecurityService iSecurityService;

    @Autowired
    public RegisterBusinessService(IUserInfoService iUserInfoService,
                                   ICategoryService iCategoryService,
                                   ISecurityService iSecurityService) {
        this.iUserInfoService = iUserInfoService;
        this.iCategoryService = iCategoryService;
        this.iSecurityService = iSecurityService;
    }

    /**
     * 用户注册
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map registerMe(Map in) throws Exception {
        String password = in.get("password").toString();
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");
        String keyToken = in.get("keyToken").toString();

        String privateKey = iSecurityService.getRSAKey(keyToken);
        password = GogoTools.decryptRSAByPrivateKey(password, privateKey);
        iSecurityService.deleteRSAKey(keyToken);

        if (phone == null && email == null) {
            //如果既没有手机，也没有邮箱，返回错误
            throw new Exception("10007");
        }

        /**
         * 检查手机是否被使用
         */
        if (phone != null) {
            UserInfo userInfo = iUserInfoService.getUserByPhone(phone);
            if (userInfo != null) {
                throw new Exception("10008");
            }
        }

        /**
         * 检查邮箱是否被使用
         */
        if (email != null) {
            UserInfo userInfo = iUserInfoService.getUserByEmail(email);
            if (userInfo != null) {
                throw new Exception("10009");
            }
        }

        UserInfo user = new UserInfo();
        user.setEmail(email);
        user.setPhone(phone);
        password = GogoTools.encoderBySHA256(password);
        password = GogoTools.encoderByMd5(password);
        user.setPassword(password);
        user.setUserId(GogoTools.UUID().toString());
        user.setToken(GogoTools.UUID().toString());
        user.setStatus(1);
        user.setCreatedTime(new Date());
        user.setTokenTime(new Date());

        iUserInfoService.createUser(user);

        /**
         * 创建用户后，自动为用户创建一个默认笔记分类
         * Default
         */
        NoteCategory noteCategory = new NoteCategory();
        noteCategory.setCategoryName("Default");
        noteCategory.setCategoryId(GogoTools.UUID().toString());
        noteCategory.setUserId(user.getUserId());
        iCategoryService.createCategory(noteCategory);

        Map out = new HashMap();
        out.put("user", user);
        return out;
    }
}
