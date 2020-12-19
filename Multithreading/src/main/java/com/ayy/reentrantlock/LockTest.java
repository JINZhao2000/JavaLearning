package com.ayy.reentrantlock;

/**
 * @ ClassName LockTest
 * @ Description the reentrant lock is exist
 * @ Author Zhao JIN
 * @ Date 12/11/2020 10
 * @ Version 1.0
 */
public class LockTest {
    public void test() {
        synchronized (this) {
            while (true) {
                synchronized (this) {
                    System.out.println("ReentrantLock");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new LockTest().test();
    }
}
