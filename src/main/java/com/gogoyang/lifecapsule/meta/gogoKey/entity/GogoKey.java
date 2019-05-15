package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GogoKey {
    private String _id;
    private String gogoKeyId;
    private String name;
    private String type;
    private Date triggerTime;
    private String url;
    private Object params;
    private String description;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGogoKeyId() {
        return gogoKeyId;
    }

    public void setGogoKeyId(String gogoKeyId) {
        this.gogoKeyId = gogoKeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
