package com.gogoyang.lifecapsule.business.admin.thread.trigger;

public class TriggerProcessor extends Thread {
    public void run() {
        System.out.println("扫描触发器...");
        /**
         * 1、读取所有trigger
         * 2、逐条检查trigger的gogokey是否被触发
         * 3、如果被触发，读取recipient的email
         * 4、读取email模板
         * 5、装载email，noteId，创建用户，通知信息，接收人的email
         * 6、发送email
         */
    }
}
