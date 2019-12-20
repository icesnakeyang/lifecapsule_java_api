package com.gogoyang.lifecapsule.meta.publicNote.entity;

import lombok.Data;

import java.util.Date;

/**
 * 公开的笔记
 */
@Data
public class PublicNote {
    private Integer ids;
    private String noteId;
    private String title;
    private String content;
    private Date createTime;
    private String userId;

    /**
     * 用户最近一次修改的时间
     */
    private Date lastTime;
}
