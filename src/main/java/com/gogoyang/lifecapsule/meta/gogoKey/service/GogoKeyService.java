package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper.GogoKeyMapper;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 触发器条件管理
 * 触发器条件分为公共触发器条件gogoPublicKey和个人触发器条件gogoKey
 * 公共触发器条件是官方定制的条件模板
 * 个人触发器条件是公共触发器条件模板的具体应用
 */
@Service
public class GogoKeyService implements IGogoKeyService {
    private final GogoKeyMapper gogoKeyMapper;

    @Autowired
    public GogoKeyService(GogoKeyMapper gogoKeyMapper) {
        this.gogoKeyMapper = gogoKeyMapper;
    }

    /**
     * 创建一个GogoKey
     *
     * @param gogoKey
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createGogoKey(GogoKey gogoKey) throws Exception {
        if (gogoKey.getGogoKeyId() == null) {
            throw new Exception("10001");
        }

        gogoKeyMapper.createGogoKey(gogoKey);
        List<KeyParam> keyParams = gogoKey.getParams();
        for (int i = 0; i < keyParams.size(); i++) {
            Map qIn = new HashMap();
            qIn.put("gogoKeyId", gogoKey.getGogoKeyId());
            if (keyParams.get(i).getParam() == null) {
                throw new Exception("10001");
            }
            qIn.put("param", keyParams.get(i).getParam());
            if (keyParams.get(i).getValue() != null) {
                qIn.put("value", keyParams.get(i).getValue());
            }
            if (keyParams.get(i).getType() == null) {
                throw new Exception("10001");
            }
            qIn.put("type", keyParams.get(i).getType());
            gogoKeyMapper.createGogoKeyParam(qIn);
        }
    }

    @Override
    public List<GogoKey> listGogoKey() throws Exception {
        Map qIn = new HashMap();
        qIn.put("keyStatus", "public");
        List<GogoKey> gogoKeys = gogoKeyMapper.listGogoKey(qIn);
        return gogoKeys;
    }

    @Override
    public GogoKey getGogoKey(String gogoPublicKeyId) throws Exception {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGogoKey(GogoKey gogoPublicKey) throws Exception {

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGogoKey(String gogoPublicKeyId) throws Exception {

    }

    @Override
    public GogoKey getGogoKeyByTriggerId(String triggerId) throws Exception {
        return null;
    }
}
