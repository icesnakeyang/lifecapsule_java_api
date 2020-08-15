package com.gogoyang.lifecapsule.meta.recipient.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Recipient {
    /**
     * 自增主键
     */
    private String ids;

    /**
     * 触发器编号
     */
    private String triggerId;

    /**
     * 接收人编号
     */
    private String recipientId;

    /**
     * 接收人姓名
     */
    private String recipientName;

    /**
     * email
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系地址，多个
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 用户对该接收人的说明
     */
    private String remark;
}
