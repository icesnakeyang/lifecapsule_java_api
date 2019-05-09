package com.gogoyang.lifecapsule.meta.recipient.entity;


import lombok.Data;

import java.util.List;

/**
 * 时空笔记发送的触发器
 * Recipient类是一个包括多个触发条件和多个接收者的MongoDB文档
 * 一个recipient里的多个条件必须同时全部满足，才能被触发
 * 一旦触发，发送给所有多个接收者
 * <p>
 * 如果用户要设置多种触发条件，在noteId上添加多个recipeint类即可
 */
@Data
public class Recipient {
    private String noteId;
    private String userId;
    private String recipientId;
    private String recipientName;

    ///////////////////////////////////////////////////////////////////////////
    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
