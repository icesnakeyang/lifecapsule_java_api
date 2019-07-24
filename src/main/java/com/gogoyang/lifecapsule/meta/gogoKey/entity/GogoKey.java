package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * GogoKey 是一个由时空笔记官方预定义的一个触发条件
 * 这个触发条件可以是通过官方或者第三方的各种url取得的各种数据的一个综合值，让用户设置这个综合值来触发条件。
 */
@Data
public class GogoKey {
    /**
     * 自增id
     */
    private Integer ids;

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 触发器条件标题
     */
    private String title;

    /**
     * 触发器条件参数
     */
    private List<KeyParam> keyParams;

    /**
     * 触发器条件描述
     */
    private String description;

    /**
     * 触发器条件Id
     */
    private String gogoKeyId;

    /**
     * 触发器条件的状态标志
     * 如果public，即表示此为公共模板
     */
    private String keyStatus;

    /**
     * 创建时间
     */
    private Date createdTime;
}
