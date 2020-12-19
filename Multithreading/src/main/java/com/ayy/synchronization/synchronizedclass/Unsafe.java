package com.ayy.synchronization.synchronizedclass;

/**
 * @ ClassName Unsafe
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:07
 * @ Version 1.0
 */
public class Unsafe implements Runnable{
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
        }else{
            System.out.println(Thread.currentThread() + " --> " + --this.numInit);
        }
        Thread.sleep(10);
    }

    public static void main (String[] args) {
        Unsafe unsafe = new Unsafe();
        Thread t1 = new Thread(unsafe);
        Thread t2 = new Thread(unsafe);
        Thread t3 = new Thread(unsafe);
        t1.start();
        t2.start();
        t3.start();
    }
}
