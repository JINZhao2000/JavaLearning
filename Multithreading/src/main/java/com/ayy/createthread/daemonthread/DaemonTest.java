package com.ayy.createthread.daemonthread;

/**
 * @ ClassName DaemonTest
 * @ Description how to create a daemon thread
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:03
 * @ Version 1.0
 */
public class DaemonTest {
    public static void main (String[] args) {
        Thread t = new Thread();
        t.setDaemon(true);
        t.start();
    }
}
