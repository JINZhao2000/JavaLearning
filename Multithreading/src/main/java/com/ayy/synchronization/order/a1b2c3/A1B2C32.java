package com.ayy.synchronization.order.a1b2c3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ ClassName A1B2C32
 * @ Description print number and letter one by one
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:12
 * @ Version 2.0
 */
public class A1B2C32 {
    static AtomicInteger cxsNum = new AtomicInteger(0);
    static volatile boolean flag = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (; 100 > cxsNum.get(); ) {
                if (!flag && (cxsNum.get() == 0 || cxsNum.incrementAndGet() % 2 == 0)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(cxsNum.get());
                    flag = true;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (; 100 > cxsNum.get(); ) {
                if (flag && (cxsNum.incrementAndGet() % 2 != 0)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(cxsNum.get());
                    flag = false;
                }
            }
        });

        t1.start();
        t2.start();
    }
}
