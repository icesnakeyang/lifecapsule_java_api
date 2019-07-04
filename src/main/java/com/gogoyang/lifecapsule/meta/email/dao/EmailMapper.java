package com.gogoyang.lifecapsule.meta.email.dao;

import com.gogoyang.lifecapsule.meta.email.entity.Email;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {
    /**
     * 添加一个email
     *
     * @param email
     */
    void addEmail(Email email);
}
