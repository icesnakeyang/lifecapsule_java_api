package com.gogoyang.lifecapsule.business.admin.gogoKey;


import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminGogoKeyBusinessService implements IAdminGogoKeyBusinessService {
    private final IGogoKeyService iGogoKeyService;

    public AdminGogoKeyBusinessService(IGogoKeyService iGogoKeyService) {
        this.iGogoKeyService = iGogoKeyService;
    }

    /**
     * 创建一个模板触发器
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

        if (type.equals("TimeTrigger")) {
            GogoPublicKey gogoPublicKey = new GogoPublicKey();
            gogoPublicKey.setTitle(title);
            gogoPublicKey.setType("TimeTrigger");
            gogoPublicKey.setParams(params);
            gogoPublicKey.setUuid(GogoTools.UUID().toString());
            iGogoKeyService.createGogoPublicKey(gogoPublicKey);
        }
    }
}
