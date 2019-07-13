package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.Date;

@Data
public class KeyParams {
    private String param;
    private String value;
    private String type;
    private Date datetime;
}
