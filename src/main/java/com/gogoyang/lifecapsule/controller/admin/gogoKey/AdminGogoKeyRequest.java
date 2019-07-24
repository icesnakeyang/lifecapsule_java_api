package com.gogoyang.lifecapsule.controller.admin.gogoKey;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import lombok.Data;

import java.util.List;

@Data
public class AdminGogoKeyRequest {
    private String title;
    private String type;
    private List<KeyParam> params;
    private String gogoPublicKeyId;
    private String description;
    private String url;
}
