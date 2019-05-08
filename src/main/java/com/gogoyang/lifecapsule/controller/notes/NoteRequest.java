package com.gogoyang.lifecapsule.controller.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户提交的笔记内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    /**
     * 用户token
     */
    private String userToken;

    /**
     * 笔记id
     */
    private String noteId;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记内容
     */
    private String detail;

    /**
     * 笔记分类
     */
    private String categoryId;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 起始页
     */
    private Integer pageIndex;

    //////////////////////////////////////////////////////////////////////////////////////////
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
