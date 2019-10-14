package com.gogoyang.lifecapsule.meta.trigger.entity;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 触发器
 * 触发器定义了一系列的触发条件和接收人
 * 当所有条件满足，即把笔记发送给所有接收人
 */
@Data
public class Trigger {
    /**
     * 自增主键
     */
    private Integer ids;

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 笔记id
     */
    private String noteId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 说明
     */
    private String remark;

    private GogoKey gogoKey;

    private List<Recipient> recipientList;
}
