package com.ayy.synchronization.synchronizedclass;

/**
 * @ ClassName Safe
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:06
 * @ Version 1.0
 */
public class Safe implements Runnable{
    private int numInit = 100;
    private boolean flag = true;
    @Override
    public void run () {
        while (flag) {
            try {
                test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test() throws InterruptedException {
        if(this.numInit<=0){
            flag = false;
            return;
        }
        synchronized (this) {
            if (this.numInit <= 0) {
                flag = false;
                return;
            }
            System.out.println(Thread.currentThread() + " --> " + --this.numInit);
        }
    }

    public static void main (String[] args) {
        Safe unsafe = new Safe();
        Thread t1 = new Thread(unsafe);
        Thread t2 = new Thread(unsafe);
        Thread t3 = new Thread(unsafe);
        t1.start();
        t2.start();
        t3.start();
    }
}
