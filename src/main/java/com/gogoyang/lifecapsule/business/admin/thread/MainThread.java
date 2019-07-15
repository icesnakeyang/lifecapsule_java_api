package com.gogoyang.lifecapsule.business.admin.thread;

import com.gogoyang.lifecapsule.business.admin.thread.trigger.TriggerProcessor;

public class MainThread extends Thread {
    public void run() {
        int count = 0;
        while (true) {
            System.out.println("启动触发器扫描处理进程...");
            Thread triggerProcessor = new TriggerProcessor();
            triggerProcessor.setName("TriggerProcessor");
            triggerProcessor.start();

            count++;
            System.out.println(getName() + "已运行"+count+"次");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
