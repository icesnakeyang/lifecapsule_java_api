package com.gogoyang.lifecapsule.business.admin.gogoKey;


import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//        String token=in.get("token").toString();
        String title = in.get("title").toString();
        String type = in.get("type").toString();
        List<KeyParams> params = (List<KeyParams>) in.get("params");

        GogoPublicKey gogoPublicKey = new GogoPublicKey();
        gogoPublicKey.setTitle(title);
        gogoPublicKey.setType("TimeTrigger");
        gogoPublicKey.setParams(params);
        gogoPublicKey.setUuid(GogoTools.UUID().toString());
        gogoPublicKey.setStatus("active");
        iGogoKeyService.createGogoPublicKey(gogoPublicKey);
    }

    @Override
    public Map listGogoPublicKey() throws Exception {
        List<GogoPublicKey> gogoPublicKeyList = iGogoKeyService.listGogoPublicKey();
        Map out = new HashMap();
        out.put("list", gogoPublicKeyList);
        return out;
    }

    @Override
    public Map getGogoPublicKey(Map in) throws Exception {
        String uuid = in.get("uuid").toString();
        GogoPublicKey gogoPublicKey = iGogoKeyService.getGogoPublicKey(uuid);
        Map out = new HashMap();
        out.put("key", gogoPublicKey);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGogoPublicKey(Map in) throws Exception {
        String token = in.get("token").toString();
        String uuid = in.get("uuid").toString();
        String type = in.get("type").toString();
        String title = in.get("title").toString();
        List<KeyParams> params = (List<KeyParams>) in.get("params");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        GogoPublicKey gogoPublicKey = iGogoKeyService.getGogoPublicKey(uuid);
        if (gogoPublicKey == null) {
            throw new Exception("no such gogo public key");
        }
        gogoPublicKey.setTitle(title);
        gogoPublicKey.setParams(params);
        gogoPublicKey.setType(type);

        iGogoKeyService.updateGogoPublicKey(gogoPublicKey);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGogoPublicKey(Map in) throws Exception {
        String token = in.get("token").toString();
        String uuid = in.get("uuid").toString();

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        iGogoKeyService.deleteGogoPublicKey(uuid);
    }
}
