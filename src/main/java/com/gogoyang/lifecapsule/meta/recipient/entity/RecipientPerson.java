package com.gogoyang.lifecapsule.meta.recipient.entity;

import lombok.Data;

import java.util.List;

/**
 * 要发送的接收人
 */
@Data
public class RecipientPerson {
    /**
     * 触发器编号
     */
    private String recipientId;

    /**
     * 个人编号
     */
    private String personId;

    /**
     * 接收人姓名
     */
    private String personName;

    /**
     * email，可以多个
     */
    private List<String> emailList;

    /**
     * 联系电话，多个
     */
    private List<String> phoneList;

    /**
     * 联系地址，多个
     */
    private List<String> addressList;

    /**
     * 用户对该接收人的说明
     */
    private String remark;

    /**
     * 扩展：qq，微信，Facebook等
     *
     * @return
     */

    /////////////////////////////////////////////////////////////////////
    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
