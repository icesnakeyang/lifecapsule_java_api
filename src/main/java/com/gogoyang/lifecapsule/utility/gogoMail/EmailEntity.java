package com.gogoyang.lifecapsule.utility.gogoMail;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailEntity implements Serializable {
    private static final long serialVersionUID=1L;

    //邮箱主机
    String host;

    //主机端口
    Integer port;

    //发送者的账号
    String userName;

    //发送者的密码
    String password;

    //发送者的邮箱地址
    String fromAddress;

    //接收者的邮箱地址
    String toAddress;

    //邮件主题
    String subject;

    //邮件内容
    String context;

    //邮件类型
    String contextType;
}
