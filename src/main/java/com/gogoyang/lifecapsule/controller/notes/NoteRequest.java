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

    private String encryptKey;

    private String data;
    private String keyToken;
    private String creativeType;

}
