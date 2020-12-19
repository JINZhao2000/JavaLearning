package com.ayy.deadlock;

/**
 * @ ClassName DeadLock
 * @ Description create a dead lock and find out by command jps -l
 * @ Author Zhao JIN
 * @ Date 10/11/2020 15:38
 * @ Version 1.0
 */

public class DeadLock {
    public static void main (String[] args) {
        Object a = new Object();
        Object b = new Object();
        new Thread(()->{
            synchronized (a) {
                System.out.println(Thread.currentThread().getName()+"I get a");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"I want get b");
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName()+"I get b");
                }
            }
        }).start();
        new Thread(()->{
            synchronized (b) {
                System.out.println(Thread.currentThread().getName()+"I get b");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"I want get a");
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName()+"I get a");
                }
            }
        }).start();
    }
}
