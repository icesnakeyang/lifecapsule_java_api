package com.gogoyang.lifecapsule.controller.notes.creativeNote;

import lombok.Data;

@Data
public class CreativeNoteRequest {
    private String detail1;
    private String detail2;
    private String detail3;
    private String detail4;
    private String noteId;
    private String categoryId;
    private String encryptKey;
    private String keyToken;

}
