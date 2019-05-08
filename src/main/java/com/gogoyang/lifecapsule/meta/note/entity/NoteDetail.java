package com.gogoyang.lifecapsule.meta.note.entity;

import lombok.Data;

/**
 * 笔记详细内容
 * 保存到MongoDB
 * 把内容和笔记信息分开保存，一是增强笔记查询速度，二是方便对内容进行碎片化处理。
 */
@Data
public class NoteDetail {
    /**
     * 全局id
     */
    private String noteId;

    /**
     * 详细内容
     */
    private String detail;

    /**
     * MongoDB自动生成的主键
     */
    private String _id;

    /////////////////////////////////////////////////////////////////////////////////


    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
