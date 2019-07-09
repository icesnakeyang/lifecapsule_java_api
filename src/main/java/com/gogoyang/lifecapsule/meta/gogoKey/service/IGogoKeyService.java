package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;

import java.util.List;

public interface IGogoKeyService {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception;

    List<GogoPublicKey> listGogoPublicKey() throws Exception;

    GogoPublicKey getGogoPublicKey(String gogoPublicKeyId) throws Exception;

    void deleteGogoPublicKey(String uuid) throws Exception;

    void updateGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception;

    GogoKey getGogoKey(String gogoKeyId) throws Exception;

    GogoKey getGogoKeyByTriggerId(String triggerId) throws Exception;
}
