package com.gogoyang.lifecapsule.controller.publicNote;

import lombok.Data;

@Data
public class PublicNoteRequest {
    private String noteId;
    private String title;
    private String content;
}
