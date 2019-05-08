package com.gogoyang.lifecapsule.controller.vo;

/**
 * Response用来装载所有要返回给前端的json数据
 */
public class Response {
    /**
     * 状态码，0表示成功，其余情况参看错误表
     */
    private Integer code=0;

    /**
     * 返回的json数据
     */
    private Object data;

    //////////////////////////////////////////////////////////////////////////////////////////
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
