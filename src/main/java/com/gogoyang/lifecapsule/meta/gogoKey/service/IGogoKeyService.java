package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;

import java.util.List;


public interface IGogoKeyService {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    List<GogoKey> listGogoPublicKeyAll() throws Exception;

    GogoKey getGogoKeyByTriggerId(String triggerId);

    GogoKey getGogoKey(String gogoPublicKeyId);

    void updateGogoKey(GogoKey gogoPublicKey);

    void deleteGogoKey(String gogoPublicKeyId);
}
