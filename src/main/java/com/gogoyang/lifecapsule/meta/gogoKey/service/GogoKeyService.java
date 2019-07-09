package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.dao.repository.IGogoKeyRepository;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GogoKeyService implements IGogoKeyService {
    private final IGogoKeyRepository iGogoKeyRepository;

    @Autowired
    public GogoKeyService(IGogoKeyRepository iGogoKeyRepository) {
        this.iGogoKeyRepository = iGogoKeyRepository;
    }

    /**
     * 创建一个GogoKey
     * @param gogoKey
     * @throws Exception
     */
    @Override
    public void createGogoKey(GogoKey gogoKey) throws Exception {
        iGogoKeyRepository.createGogoKey(gogoKey);
    }

    @Override
    public void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception {
        iGogoKeyRepository.createGogoPublicKey(gogoPublicKey);
    }

    @Override
    public List<GogoPublicKey> listGogoPublicKey() throws Exception {
        List<GogoPublicKey> gogoPublicKeyList=iGogoKeyRepository.listGogoPublicKey();
        return gogoPublicKeyList;
    }

    @Override
    public GogoPublicKey getGogoPublicKey(String gogoPublicKeyId) throws Exception {
        GogoPublicKey gogoPublicKey=iGogoKeyRepository.getGogoPublicKey(gogoPublicKeyId);
        return gogoPublicKey;
    }

    @Override
    public void deleteGogoPublicKey(String uuid) throws Exception {
        iGogoKeyRepository.deleteGogoPublicKey(uuid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception {
        iGogoKeyRepository.updateGogoPublicKey(gogoPublicKey);
    }

    @Override
    public GogoKey getGogoKey(String gogoKeyId) throws Exception {
        GogoKey gogoKey=iGogoKeyRepository.getGogoKey(gogoKeyId);
        return gogoKey;
    }

    @Override
    public GogoKey getGogoKeyByTriggerId(String triggerId) throws Exception {
        GogoKey gogoKey=iGogoKeyRepository.getGogoKeyByTriggerId(triggerId);
        return gogoKey;
    }
}
