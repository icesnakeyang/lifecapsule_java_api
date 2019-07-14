package com.gogoyang.lifecapsule.business.admin.thread;

import com.gogoyang.lifecapsule.business.admin.thread.trigger.TriggerProcessor;

public class MainThread extends Thread {
    public void run() {
        while (true) {
            System.out.println("启动触发器扫描处理进程...");
            Thread triggerProcessor = new TriggerProcessor();
            triggerProcessor.setName("TriggerProcessor");
            triggerProcessor.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
