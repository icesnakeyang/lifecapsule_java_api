package com.gogoyang.lifecapsule.meta.userData.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户的api转存数据
 */
@Data
public class UserData {
    private Integer ids;
    private String dataId;
    private String dataToken;
    private Date dataTokenTime;
    private String noteId;
}
