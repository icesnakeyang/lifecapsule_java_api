package com.gogoyang.lifecapsule.meta.note.entity;

import lombok.Data;

import java.util.Date;

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
    private String categoryName;

    private String userEncodeKey;
    private String noteType;
}
