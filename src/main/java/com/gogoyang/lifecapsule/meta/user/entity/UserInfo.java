package com.gogoyang.lifecapsule.meta.user.entity;


import lombok.Data;

import java.util.Date;


@Data
public class UserInfo {
    /**
     * id
     */
    private String userId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 注册时间
     */
    private Date createdTime;

    /**
     * 每个用户注册后都会自动生成一个token作为用户唯一标识
     * token每隔一段时间会自动刷新，以保障用户安全
     */
    private String token;

    /**
     * 当前token产生的时间
     */
    private Date tokenTime;

    /**
     * 当前用户状态
     */
    private Integer status;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    ////////////////////////////////////////////////////////////////////////////////////

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Date tokenTime) {
        this.tokenTime = tokenTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
