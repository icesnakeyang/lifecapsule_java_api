package com.gogoyang.lifecapsule.meta.email.entity;

import lombok.Data;

import java.util.Date;

/**
 * 接收人的email
 */
@Data
public class Email {
    /**
     * 自增主键
     */
    private Integer ids;

    /**
     * email id
     */
    private String emailId;

    private String email;

    /**
     * 用户添加email的时间
     */
    private Date createdTime;

    /**
     * 当前状态
     */
    private String status;
}
