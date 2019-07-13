package com.gogoyang.lifecapsule.meta.gogoKey.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * GogoKey 是一个由时空笔记官方预定义的一个触发条件
 * 这个触发条件可以是通过官方或者第三方的各种url取得的各种数据的一个综合值，让用户设置这个综合值来触发条件。
 * GogoKey的Id，name是唯一字段，存储在mysql数据库，其余配置内容储存在MongoDB
 */
@Data
public class GogoKey {
    private String _id;
    private String gogoKeyId;
    private String gogoPublicKeyId;
    private String title;
    private List<KeyParams> params;
    private String triggerId;
    private String description;
    private Date tt1;
}
