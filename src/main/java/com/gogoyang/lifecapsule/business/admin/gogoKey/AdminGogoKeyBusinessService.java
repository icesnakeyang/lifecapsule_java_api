package com.gogoyang.lifecapsule.business.admin.gogoKey;


import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminGogoKeyBusinessService implements IAdminGogoKeyBusinessService{
    private final IGogoKeyService iGogoKeyService;

    public AdminGogoKeyBusinessService(IGogoKeyService iGogoKeyService) {
        this.iGogoKeyService = iGogoKeyService;
    }

    @Override
    public void createGogoPublicKey(Map in) throws Exception {
        String token=in.get("token").toString();
        String title=in.get("title").toString();
        List<KeyParams> params=(List<KeyParams>)in.get("params");

        GogoKey gogoKey=new GogoKey();
        gogoKey.setName(title);

        gogoKey.setParamsList(params);
        return;

    }
}
