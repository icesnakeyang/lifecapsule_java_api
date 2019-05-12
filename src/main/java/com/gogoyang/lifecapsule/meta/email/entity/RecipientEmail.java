package com.gogoyang.lifecapsule.meta.email.entity;

import lombok.Data;

import java.util.Date;

/**
 * 接收人的email
 */
@Data
public class RecipientEmail {
    /**
     * 自增主键
     */
    private Integer ids;
    /**
     * email id
     */
    private String emailId;
    /**
     * 接收人id
     */
    private String recipientId;
    private String email;
    /**
     * 用户添加email的时间
     */
    private Date createdTime;
    /**
     * email验证码
     */
    private String verifyCode;
    /**
     * 发送email验收码给用户的时间
     */
    private Date verifyTime;
    /**
     * 用户通过email验收的时间
     */
    private Date passTime;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }
}
