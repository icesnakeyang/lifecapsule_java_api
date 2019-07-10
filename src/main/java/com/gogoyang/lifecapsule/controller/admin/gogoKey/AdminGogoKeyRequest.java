package com.gogoyang.lifecapsule.controller.admin.gogoKey;

import lombok.Data;

@Data
public class AdminGogoKeyRequest {
    private String title;
    private String type;
    private Object params;
    private String gogoPublicKeyId;
    private String description;
    private String url;
}
