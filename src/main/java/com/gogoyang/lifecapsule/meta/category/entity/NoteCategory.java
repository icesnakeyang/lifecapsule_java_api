package com.gogoyang.lifecapsule.meta.category.entity;

import lombok.Data;

/**
 * 笔记分类
 * 用户自行创建分类
 */
@Data
public class NoteCategory {
    /**
     * 自增id，主键
     */
    private Integer ids;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 用户id
     */
    private String userId;

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
