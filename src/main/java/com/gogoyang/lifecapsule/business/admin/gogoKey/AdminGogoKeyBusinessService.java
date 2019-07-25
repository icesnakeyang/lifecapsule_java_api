package com.gogoyang.lifecapsule.business.admin.gogoKey;


import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminGogoKeyBusinessService implements IAdminGogoKeyBusinessService {
    private final IGogoKeyService iGogoKeyService;
    private final IUserInfoService iUserInfoService;

    public AdminGogoKeyBusinessService(IGogoKeyService iGogoKeyService,
                                       IUserInfoService iUserInfoService) {
        this.iGogoKeyService = iGogoKeyService;
        this.iUserInfoService = iUserInfoService;
    }

    /**
     * 创建一个模板触发器
     *
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createGogoPublicKey(Map in) throws Exception {
        String token = in.get("token").toString();
        String title = in.get("title").toString();
        List<KeyParam> keyParams = (List<KeyParam>) in.get("keyParams");
        String url = (String) in.get("url");
        String description = (String) in.get("description");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        GogoKey gogoPublicKey = new GogoKey();
        gogoPublicKey.setTitle(title);
        gogoPublicKey.setKeyParams(keyParams);
        gogoPublicKey.setGogoKeyId(GogoTools.UUID().toString());
        gogoPublicKey.setKeyStatus("active");
        gogoPublicKey.setDescription(description);
        gogoPublicKey.setCreatedTime(new Date());
        gogoPublicKey.setKeyStatus("publicKey");
        iGogoKeyService.createGogoKey(gogoPublicKey);
    }

    @Override
    public Map listGogoPublicKey(Map in) throws Exception {
        String token = in.get("token").toString();

        List<GogoKey> gogoPublicKeyList = iGogoKeyService.listGogoPublicKeyAll();
        Map out = new HashMap();
        out.put("gogoPublicKeyList", gogoPublicKeyList);
        return out;
    }

    @Override
    public Map getGogoPublicKey(Map in) throws Exception {
        String gogoPublicKeyId = in.get("gogoKeyId").toString();
        GogoKey gogoPublicKey = iGogoKeyService.getGogoKeyByGogoKeyId(gogoPublicKeyId);
        Map out = new HashMap();
        out.put("key", gogoPublicKey);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGogoPublicKey(Map in) throws Exception {
        String token = in.get("token").toString();
        String uuid = in.get("gogoKeyId").toString();
        String title = in.get("title").toString();
        String description = in.get("description").toString();
        List<KeyParam> keyParams = (List<KeyParam>) in.get("keyParams");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        GogoKey gogoPublicKey = iGogoKeyService.getGogoKey(uuid);
        if (gogoPublicKey == null) {
            throw new Exception("no such gogo public key");
        }
        gogoPublicKey.setTitle(title);
        gogoPublicKey.setKeyParams(keyParams);

        iGogoKeyService.updateGogoKey(gogoPublicKey);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGogoPublicKey(Map in) throws Exception {
        String token = in.get("token").toString();
        String gogoPublicKeyId = in.get("gogoKeyId").toString();

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        iGogoKeyService.deleteGogoKey(gogoPublicKeyId);
    }
}
