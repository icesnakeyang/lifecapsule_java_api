package com.gogoyang.lifecapsule.controller.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String phone;
    private String email;
    private String password;
    private String keyToken;
    private String deviceId;
    private String nickname;
}
