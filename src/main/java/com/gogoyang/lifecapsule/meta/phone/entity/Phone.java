package com.gogoyang.lifecapsule.meta.phone.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Phone {
    private Integer ids;
    private String phoneId;
    private String phone;
    private Date createdTime;
    private String status;
}
