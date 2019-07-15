package com.gogoyang.lifecapsule.business.admin.thread.trigger;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GogoKeyTrigger {
    public List<GogoKey> listGogoKey() {
        List<GogoKey> gogoKeys = null;
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gogoKeys;
    }
}
