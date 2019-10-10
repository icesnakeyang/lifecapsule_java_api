package com.gogoyang.lifecapsule.controller.trigger;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private List<KeyParam> keyParams;
    private String gogoPublicKeyId;
    private String gogoKeyTitle;
    private String gogoKeyId;
    private String title;
    private String description;
    private GogoKey gogoKey;
}
