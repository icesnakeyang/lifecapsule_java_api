package com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GogoKeyMapper {

    void createGogoKey(GogoKey gogoKey);
    void createGogoKeyParam(Map qIn);

    List<GogoKey> listGogoKey(Map qIn);
}
