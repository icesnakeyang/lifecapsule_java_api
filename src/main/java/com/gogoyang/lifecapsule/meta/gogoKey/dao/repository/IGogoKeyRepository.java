package com.gogoyang.lifecapsule.meta.gogoKey.dao.repository;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;

import java.util.List;

public interface IGogoKeyRepository {
    void createGogoKey(GogoKey gogoKey) throws Exception;

    void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception;

    List<GogoPublicKey> listGogoPublicKey() throws Exception;

    GogoPublicKey getGogoPublicKey(String uuid) throws Exception;

    void deleteGogoPublicKey(String uuid) throws Exception;

    void updateGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception;
}
