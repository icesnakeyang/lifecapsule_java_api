package com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import org.apache.ibatis.annotations.Mapper;
import sun.swing.StringUIClientPropertyKey;

import java.util.List;
import java.util.Map;

@Mapper
public interface GogoKeyMapper {

    void createGogoKey(GogoKey gogoKey);
    void createGogoKeyParam(Map qIn);

    List<GogoKey> listGogoPublicKeyAll();

    List<KeyParam> listKeyParamsByGogoKeyId(String gogoKeyId);

    /**
     * 根据gogoKeyId查询触发器条件详情
     * @param gogoKeyId
     * @return
     */
    GogoKey getGogoKeyByGogoKeyId(String gogoKeyId);

    void updateGogoKey(GogoKey gogoKey);

    void updateGogoKeyParam(Map qIn);

    void deleteGogoKeyParamByGogoKeyId(String gogoKeyId);

    GogoKey getGogoKeyByTriggerid(String triggerId);

    List<GogoKey> listUserGogoKey(Map qIn);

    void setGogoKeyTriggered(String gogoKeyId);

    void deleteGogoKeyByTriggerId(String triggerId);

    /**
     * 创建一个公共触发器模板
     * @param gogoKey
     */
    void createGogoPublicKey(GogoKey gogoKey);

    /**
     * 创建公共触发器模板的参数
     * @param qIn
     */
    void createGogoPublicKeyParam(Map qIn);

    /**
     * 根据publicKeyId读取公共触发器模板内容
     * @param gogoPublicKeyId
     * @return
     * @throws Exception
     */
    GogoKey getPublicKeyByPublicKeyId(String gogoPublicKeyId);

    List<KeyParam> listPublicKeyParamsByPublicKeyId(String gogoPublicKeyId);
}
