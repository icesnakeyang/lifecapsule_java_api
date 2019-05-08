package com.gogoyang.lifecapsule.meta.recipient.entity;


import lombok.Data;

import java.util.List;

/**
 * Recipient类是一个包括多个触发条件和多个接收者的MongoDB文档
 * 一个recipient里的多个条件必须同时全部满足，才能被触发
 * 一旦触发，发送给所有多个接收者
 *
 * 如果用户要设置多种触发条件，在noteId上添加多个recipeint类即可
 */
@Data
public class Recipient {
    private String noteId;
    private String userId;
    private String recipientId;
    List<RecipientPerson> personList;

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

    public List<RecipientPerson> getPersonList() {
        return personList;
    }

    public void setPersonList(List<RecipientPerson> personList) {
        this.personList = personList;
    }
}
