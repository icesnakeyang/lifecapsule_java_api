package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper.GogoKeyMapper;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import com.gogoyang.lifecapsule.utility.GogoTools;
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
     * 创建一个用户的触发器GogoKey
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
        List<KeyParam> keyParams = gogoKey.getKeyParams();
        for (int i = 0; i < keyParams.size(); i++) {
            Map qIn = new HashMap();
            qIn.put("paramId", GogoTools.UUID().toString());
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

    /**
     * 创建一个公共触发器模板
     * @param gogoKey
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createGogoPublicKey(GogoKey gogoKey) throws Exception {
        if (gogoKey.getGogoKeyId() == null) {
            throw new Exception("10001");
        }

        gogoKeyMapper.createGogoPublicKey(gogoKey);
        List<KeyParam> keyParams = gogoKey.getKeyParams();
        for (int i = 0; i < keyParams.size(); i++) {
            Map qIn = new HashMap();
            qIn.put("paramId", GogoTools.UUID().toString());
            qIn.put("gogoPublicKeyId", gogoKey.getGogoKeyId());
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
            gogoKeyMapper.createGogoPublicKeyParam(qIn);
        }
    }

    @Override
    public List<GogoKey> listGogoPublicKeyAll() throws Exception {
        List<GogoKey> gogoKeys = gogoKeyMapper.listGogoPublicKeyAll();
        return gogoKeys;
    }

    /**
     * 根据gogoKeyId查询触发器条件详情
     *
     * @param gogoKeyId
     * @return
     * @throws Exception
     */
    @Override
    public GogoKey getGogoKeyByGogoKeyId(String gogoKeyId) throws Exception {
        GogoKey gogoKey = gogoKeyMapper.getGogoKeyByGogoKeyId(gogoKeyId);
        List<KeyParam> keyParams = gogoKeyMapper.listKeyParamsByGogoKeyId(gogoKeyId);
        gogoKey.setKeyParams(keyParams);
        return gogoKey;
    }

    @Override
    public GogoKey getGogoKeyByTriggerId(String triggerId) {
        GogoKey gogoKey = gogoKeyMapper.getGogoKeyByTriggerid(triggerId);
        if (gogoKey != null) {
            List<KeyParam> keyParams = gogoKeyMapper.listKeyParamsByGogoKeyId(gogoKey.getGogoKeyId());
            gogoKey.setKeyParams(keyParams);
        }
        return gogoKey;
    }

    @Override
    public GogoKey getPublicKey(String gogoPublicKeyId) throws Exception {
        GogoKey publicKey = gogoKeyMapper.getPublicKeyByPublicKeyId(gogoPublicKeyId);
        List<KeyParam> keyParams = gogoKeyMapper.listPublicKeyParamsByPublicKeyId(gogoPublicKeyId);
        publicKey.setKeyParams(keyParams);
        return publicKey;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGogoKey(GogoKey gogoKey) throws Exception {
        if (gogoKey.getGogoKeyId() == null) {
            throw new Exception("10001");
        }
        gogoKeyMapper.updateGogoKey(gogoKey);
        List<KeyParam> keyParams = gogoKey.getKeyParams();
        /**
         * 先把数据库原来的所有数据都删除，再新增回去
         */
        gogoKeyMapper.deleteGogoKeyParamByGogoKeyId(gogoKey.getGogoKeyId());
        for (int i = 0; i < keyParams.size(); i++) {
            Map qIn = new HashMap();
            qIn.put("paramId", GogoTools.UUID().toString());
            qIn.put("gogoKeyId", gogoKey.getGogoKeyId());
            qIn.put("type", keyParams.get(i).getType());
            qIn.put("param", keyParams.get(i).getParam());
            qIn.put("value", keyParams.get(i).getValue());
            gogoKeyMapper.createGogoKeyParam(qIn);
        }
    }

    @Override
    public void deleteGogoKey(String gogoPublicKeyId) {

    }

    @Override
    public List<GogoKey> listGogoKeyAll() throws Exception {
        Map qIn = new HashMap();
        qIn.put("offset", 0);
        qIn.put("size", 10000);
        List<GogoKey> gogoKeys = gogoKeyMapper.listUserGogoKey(qIn);
        return gogoKeys;
    }

    /**
     * gogoKey已经被处理了，设置为processed
     *
     * @param gogoKeyId
     * @throws Exception
     */
    @Override
    public void setGogoKeyTriggered(String gogoKeyId) throws Exception {
        gogoKeyMapper.setGogoKeyTriggered(gogoKeyId);
    }

    @Override
    public void deleteGogoKeyByTriggerId(String triggerId) throws Exception {
        GogoKey gogoKey=getGogoKeyByTriggerId(triggerId);
        if(gogoKey!=null) {
            gogoKeyMapper.deleteGogoKeyParamByGogoKeyId(gogoKey.getGogoKeyId());
            gogoKeyMapper.deleteGogoKeyByTriggerId(triggerId);
        }
    }

    @Override
    public void deleteKeyParamsByGogokeyId(String gogoKeyId) throws Exception {
        gogoKeyMapper.deleteGogoKeyParamByGogoKeyId(gogoKeyId);
    }
}
