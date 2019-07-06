package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TimeTrigger {
    private String _id;
    private String keyType;
    private Date value;
}
