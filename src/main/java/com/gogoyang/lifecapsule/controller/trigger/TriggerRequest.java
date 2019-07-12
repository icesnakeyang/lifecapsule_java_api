package com.gogoyang.lifecapsule.controller.trigger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TriggerRequest {
    private String userId;
    private String noteId;
    private String recipientId;
    private String recipientName;
    private String email;
    private String phone;
    private String address;
    private String remark;
    private String triggerRemark;
    private String triggerName;
    private String triggerId;
    private Object params;
    private String gogoPublicKeyId;
    private String gogoKeyTitle;
    private String gogoKeyId;
}
