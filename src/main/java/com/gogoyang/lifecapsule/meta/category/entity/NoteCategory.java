package com.gogoyang.lifecapsule.meta.category.entity;

import lombok.Data;

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

    private String noteType;
}
