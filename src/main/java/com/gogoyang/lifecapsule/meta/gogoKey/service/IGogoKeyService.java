package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;

import java.util.List;


public interface IGogoKeyService {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    List<GogoKey> listGogoKey() throws Exception;

    GogoKey getGogoKey(String gogoPublicKeyId) throws Exception;

    void updateGogoKey(GogoKey gogoPublicKey) throws Exception;

    void deleteGogoKey(String gogoPublicKeyId) throws Exception;

    GogoKey getGogoKeyByTriggerId(String triggerId) throws Exception;
}
