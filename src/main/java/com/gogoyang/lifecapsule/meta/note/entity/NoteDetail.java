package com.gogoyang.lifecapsule.meta.note.entity;

import lombok.Data;

/**
 * 笔记详细内容
 * 保存到MongoDB
 * 把内容和笔记信息分开保存，一是增强笔记查询速度，二是方便对内容进行碎片化处理。
 */
@Data
public class NoteDetail {
    private Integer ids;
    /**
     * 全局id
     */
    private String noteId;

    /**
     * 碎片id
     */
    private String contentId;

    /**
     * 详细内容
     */
    private String content;
}
