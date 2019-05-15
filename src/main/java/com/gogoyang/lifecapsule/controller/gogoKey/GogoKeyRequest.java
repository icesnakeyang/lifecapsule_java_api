package com.gogoyang.lifecapsule.controller.gogoKey;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GogoKeyRequest {
    private String _id;
    private String gogoKeyId;
    private String name;
    private String type;
    private String triggerTime;
    private String url;
    private Object params;
    private String description;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public String getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(String triggerTime) {
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
