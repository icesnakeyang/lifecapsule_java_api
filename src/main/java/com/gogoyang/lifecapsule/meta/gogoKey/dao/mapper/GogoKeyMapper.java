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
}
