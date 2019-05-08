package com.gogoyang.lifecapsule.meta.note.entity;

import lombok.Data;

import java.util.Date;

/**
 * 笔记信息类
 * 不包括笔记内容，详细内容保存在NoteDetail里
 * 保存到MySQL
 */
@Data
public class NoteInfo {
    /**
     * 全局id
     */
    private String noteId;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建用户id
     */
    private String userId;

    /**
     * 笔记详情
     */
    private String detail;

    /**
     * 笔记分类
     */
    private String categoryId;

    //////////////////////////////////////////////////////////////////////////////


    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
