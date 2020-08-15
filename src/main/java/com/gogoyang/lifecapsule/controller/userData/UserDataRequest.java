package com.gogoyang.lifecapsule.controller.userData;

import lombok.Data;

@Data
public class UserDataRequest {
    private String dataToken;
    private String encryptKey;
    private String keyToken;
    private String noteId;
    private String userToken;
}
