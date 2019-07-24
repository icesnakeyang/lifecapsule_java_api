package com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GogoKeyMapper {

    void createGogoKey(GogoKey gogoKey);
    void createGogoKeyParam(Map qIn);

    List<GogoKey> listGogoPublicKeyAll();

    List<KeyParam> listKeyParamsByGogoKeyId(String gogoKeyId);
}
