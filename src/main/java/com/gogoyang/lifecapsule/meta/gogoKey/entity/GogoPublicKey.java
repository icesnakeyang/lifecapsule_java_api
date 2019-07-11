package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.List;

@Data
public class GogoPublicKey {
    private String _id;
    private String gogoPublicKeyId;
    private String title;
    private List<KeyParams> params;
    private String status;
    private String description;
}
