package com.gogoyang.lifecapsule.meta.recipient.entity;


import com.sun.xml.internal.ws.spi.db.DatabindingException;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 接收人
 */
@Data
public class Recipient {
    /**
     * MongoDB自动创建的id
     */
    private String _id;
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
    private String name;

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
     * 创建时间
     */
    private Date createdTime;

    /**
     * 用户对该接收人的说明
     */
    private String remark;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * 扩展：qq，微信，Facebook等
     *
     * @return
     */

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
