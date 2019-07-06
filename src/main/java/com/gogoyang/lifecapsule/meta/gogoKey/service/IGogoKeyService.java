package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;

import java.util.List;

public interface IGogoKeyService {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    void createGogoPublicKey(GogoKey gogoKey) throws Exception;

    List<GogoKey> listGogokey() throws Exception;
}
