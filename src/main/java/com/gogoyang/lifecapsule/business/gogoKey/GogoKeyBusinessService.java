package com.gogoyang.lifecapsule.business.gogoKey;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GogoKeyBusinessService implements IGogoKeyBusinessService {
    private final IGogoKeyService iGogoKeyService;

    @Autowired
    public GogoKeyBusinessService(IGogoKeyService iGogoKeyService) {
        this.iGogoKeyService = iGogoKeyService;
    }

    @Override
    public Map createGogoKey(Map in) throws Exception {
        String name = in.get("name").toString();
        String type = in.get("type").toString();
        String description = (String) in.get("description");
        Object params = in.get("params");
        Date triggerTime = (Date) in.get("triggerTime");
        String url = (String) in.get("url");

        GogoKey gogoKey = new GogoKey();
        gogoKey.setGogoKeyId(GogoTools.UUID().toString());
        gogoKey.setKeyStatus("userKey");

        iGogoKeyService.createGogoKey(gogoKey);

        Map out = new HashMap();
        out.put("gogoKey", gogoKey);
        return out;
    }
}
