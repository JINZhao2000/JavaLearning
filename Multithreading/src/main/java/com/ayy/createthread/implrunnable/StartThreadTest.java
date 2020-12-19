package com.ayy.createthread.implrunnable;

/**
 * @ ClassName StartThreadTest
 * @ Description the run of thread decided by cpu
 * @ Author Zhao JIN
 * @ Date 09/11/2020 15:31
 * @ Version 1.0
 */
public class StartThreadTest implements Runnable{
    @Override
    public void run () {
        for (int i = 0; i <20; i++) {
            System.out.println("Thread_class");
        }
    }

    public static void main (String[] args) throws InterruptedException {
        new Thread(new StartThreadTest()).start();
        for (int i = 0; i < 20; i++) {
            System.out.println("Thread_main");
        }
    }
}
