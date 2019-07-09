package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper.GogoKeyMapper;
import com.gogoyang.lifecapsule.meta.gogoKey.dao.repository.IGogoKeyRepository;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GogoKeyService implements IGogoKeyService {
    private final IGogoKeyRepository iGogoKeyRepository;
    private final GogoKeyMapper gogoKeyMapper;

    @Autowired
    public GogoKeyService(IGogoKeyRepository iGogoKeyRepository,
                          GogoKeyMapper gogoKeyMapper) {
        this.iGogoKeyRepository = iGogoKeyRepository;
        this.gogoKeyMapper = gogoKeyMapper;
    }

    /**
     * 创建一个GogoKey
     * @param gogoKey
     * @throws Exception
     */
    @Override
    public void createGogoKey(GogoKey gogoKey) throws Exception {
        gogoKeyMapper.createGogoKey(gogoKey);
        iGogoKeyRepository.createGogoKey(gogoKey);
    }

    @Override
    public void createGogoPublicKey(GogoPublicKey gogoPublicKey) throws Exception {
        iGogoKeyRepository.createGogoPublicKey(gogoPublicKey);
    }

    /**
     * 读取GogoKey列表
     * @return
     * @throws Exception
     */
    @Override
    public List<GogoKey> listGogokey() throws Exception {
        Map qIn = new HashMap();
        qIn.put("indexStart", 0);
        qIn.put("offset", 0);
        ArrayList<GogoKey> gogoKeys = gogoKeyMapper.listGogoKey(qIn);
        return gogoKeys;
    }

    @Override
    public List<GogoPublicKey> listGogoPublicKey() throws Exception {
        List<GogoPublicKey> gogoPublicKeyList=iGogoKeyRepository.listGogoPublicKey();
        return gogoPublicKeyList;
    }

    @Override
    public GogoPublicKey getGogoPublicKey(String uuid) throws Exception {
        GogoPublicKey gogoPublicKey=iGogoKeyRepository.getGogoPublicKey(uuid);
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
}
