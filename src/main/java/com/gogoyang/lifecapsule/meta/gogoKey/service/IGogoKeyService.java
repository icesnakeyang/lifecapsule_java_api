package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;

import java.util.List;


public interface IGogoKeyService {
    /**
     * 创建一个用户的触发器GogoKey
     * @param gogoKey
     * @throws Exception
     */
    void createGogoKey(GogoKey gogoKey) throws Exception;

    /**
     * 创建一个公共触发器模板
     * @param gogoKey
     * @throws Exception
     */
    void createGogoPublicKey(GogoKey gogoKey) throws Exception;

    /**
     * 读取公共模板触发器列表
     * @return
     * @throws Exception
     */
    List<GogoKey> listGogoPublicKeyAll() throws Exception;

    /**
     * 根据gogoKeyId查询触发器条件详情
     *
     * @param gogoKeyId
     * @return
     * @throws Exception
     */
    GogoKey getGogoKeyByGogoKeyId(String gogoKeyId) throws Exception;

    GogoKey getGogoKeyByTriggerId(String triggerId) throws Exception;

    GogoKey getPublicKey(String gogoPublicKeyId) throws Exception;

    void updateGogoKey(GogoKey gogoPublicKey) throws Exception;

    void deleteGogoKey(String gogoPublicKeyId) throws Exception;

    /**
     * 读取用户设置的gogoKey，让后台检查处理
     * @return
     * @throws Exception
     */
    List<GogoKey> listGogoKeyAll() throws Exception;

    void setGogoKeyTriggered(String gogoKeyId) throws Exception;

    void deleteGogoKeyByTriggerId(String triggerId) throws Exception;

    void deleteKeyParamsByGogokeyId(String gogoKeyId) throws Exception;
}
