package com.gogoyang.lifecapsule.meta.recipient.entity;



import com.gogoyang.lifecapsule.meta.email.entity.RecipientEmail;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 接收人
 */
@Data
public class Recipient {
    /**
     * 自增主键
     */
    private String ids;
    /**
     * 触发器编号
     */
    private String triggerId;

    /**
     * 接收人编号
     */
    private String recipientId;

    /**
     * 接收人姓名
     */
    private String recipientName;

    /**
     * email，可以多个
     */
    private List<RecipientEmail> emailList;

    /**
     * 联系电话，多个
     */
    private List<String> phoneList;

    /**
     * 联系地址，多个
     */
    private List<String> addressList;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 用户对该接收人的说明
     */
    private String remark;
    ///////////////////////////////////////////////////////////////////////////

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public List<RecipientEmail> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<RecipientEmail> emailList) {
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
