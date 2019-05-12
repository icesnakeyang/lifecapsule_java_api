package com.gogoyang.lifecapsule.meta.phone.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RecipientPhone {
    private Integer ids;
    private String recipientId;
    private String phoneId;
    private String phone;
    private Date createdTime;
    private String remark;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
