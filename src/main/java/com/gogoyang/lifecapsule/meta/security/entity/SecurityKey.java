package com.gogoyang.lifecapsule.meta.security.entity;

import lombok.Data;

@Data
public class SecurityKey {
    private String keyToken;
    private String privateRSA;
}
