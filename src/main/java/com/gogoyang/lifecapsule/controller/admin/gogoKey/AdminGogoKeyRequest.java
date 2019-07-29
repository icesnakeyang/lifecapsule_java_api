package com.gogoyang.lifecapsule.controller.admin.gogoKey;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import lombok.Data;

import java.util.List;

@Data
public class AdminGogoKeyRequest {
    private String title;
    private String type;
//    private Object keyParams;
    private List<KeyParam> keyParams;
    private String gogoKeyId;
    private String description;
    private String url;
}
