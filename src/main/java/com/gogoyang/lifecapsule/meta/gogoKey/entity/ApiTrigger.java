package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.List;

@Data
public class ApiTrigger {
    private String _id;
    private String url;
    private List<KeyParams> paramsList;
}
