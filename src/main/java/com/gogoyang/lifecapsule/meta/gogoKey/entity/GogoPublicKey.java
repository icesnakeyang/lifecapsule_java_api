package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.List;

@Data
public class GogoPublicKey {
    private String _id;
    private String uuid;
    private String title;
    private String type;
    private List<KeyParams> params;
}
