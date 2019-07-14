package com.gogoyang.lifecapsule.business.admin.thread;


public class Stage extends Thread {
    public void run() {
        ArmyRunable armyTaskOfSuiDynasty = new ArmyRunable();
        ArmyRunable armyTaskOfRevolt = new ArmyRunable();

        Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty, "隋军");
        Thread armyOfRevolt = new Thread(armyTaskOfRevolt, "农民起义军");

        armyOfSuiDynasty.start();
        armyOfRevolt.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        armyTaskOfSuiDynasty.keepRunning = false;
        armyTaskOfRevolt.keepRunning = false;

        try {
            armyOfRevolt.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Stage().start();
    }
}
