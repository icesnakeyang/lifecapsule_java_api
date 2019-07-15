package com.gogoyang.lifecapsule.business.admin.thread.trigger;


import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThreadTriggerBusinessService implements IThreadTriggerBusinessService{
    private final IGogoKeyService iGogoKeyService;

    public ThreadTriggerBusinessService(IGogoKeyService iGogoKeyService) {
        this.iGogoKeyService = iGogoKeyService;
    }

    @Override
    public void loadAndCheckGogoKey() throws Exception {
        List<GogoKey> gogoKeyList=iGogoKeyService.listGogoKey();

        for(int i=0;i<gogoKeyList.size();i++){
            GogoKey gogoKey=gogoKeyList.get(i);

            for(int k=0;k<gogoKey.getParams().size();k++){
                KeyParams key=gogoKey.getParams().get(k);

                if(key.getType().equals("datetime")){

                }
            }
        }
    }
}
