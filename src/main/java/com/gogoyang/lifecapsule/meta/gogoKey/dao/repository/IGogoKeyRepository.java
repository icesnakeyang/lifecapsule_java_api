package com.gogoyang.lifecapsule.meta.gogoKey.dao.repository;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;

public interface IGogoKeyRepository {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception;
}
