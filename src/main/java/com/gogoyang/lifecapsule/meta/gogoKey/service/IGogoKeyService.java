package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;

import java.util.List;

public interface IGogoKeyService {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception;

    List<GogoKey> listGogokey() throws Exception;
}
