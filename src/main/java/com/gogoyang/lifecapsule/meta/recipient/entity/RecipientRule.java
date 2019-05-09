package com.gogoyang.lifecapsule.meta.recipient.entity;

import lombok.Data;

import java.util.Date;

/**
 * 触发器条件
 */
@Data
public class RecipientRule {
    private String recipientId;
    private String recipientRuleId;
    private String name;
    private String key;
    private String value;
    private String description;
    private Date createdTime;
    private String createdUser;

    ////////////////////////////////////////////////////////////////////////////////////

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientRuleId() {
        return recipientRuleId;
    }

    public void setRecipientRuleId(String recipientRuleId) {
        this.recipientRuleId = recipientRuleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
}
