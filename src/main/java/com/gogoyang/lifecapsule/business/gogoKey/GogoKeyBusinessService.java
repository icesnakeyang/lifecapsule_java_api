package com.gogoyang.lifecapsule.business.gogoKey;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        gogoKey.setName(name);
        gogoKey.setType(type);
        if (description != null) {
            gogoKey.setDescription(description);
        }
        if (params != null) {
            gogoKey.setParams(params);
        }
        if (triggerTime != null) {
            gogoKey.setTriggerTime(triggerTime);
        }
        if (url != null) {
            gogoKey.setUrl(url);
        }
        iGogoKeyService.createGogoKey(gogoKey);

        Map out = new HashMap();
        out.put("gogoKey", gogoKey);
        return out;
    }

    @Override
    public Map listGogoKey(Map in) throws Exception {
        List<GogoKey> gogoKeyList = iGogoKeyService.listGogokey();
        Map out = new HashMap();
        out.put("gogoKeyList", gogoKeyList);
        return out;
    }
}