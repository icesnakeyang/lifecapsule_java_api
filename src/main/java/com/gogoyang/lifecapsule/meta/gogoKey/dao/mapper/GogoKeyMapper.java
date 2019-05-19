package com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface GogoKeyMapper {
    /**
     * 创建一个GogoKey
     * @param gogoKey
     */
    void createGogoKey(GogoKey gogoKey);

    /**
     * 读取GogoKey列表
     * @param qIn
     * @return
     */
    ArrayList<GogoKey> listGogoKey(Map qIn);
}
