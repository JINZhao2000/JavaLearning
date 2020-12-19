package com.ayy.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ ClassName CASTest
 * @ Description compare and swap
 * @ Author Zhao JIN
 * @ Date 12/11/2020 12
 * @ Version 1.0
 */
public class CASTest {
    private static AtomicInteger stock = new AtomicInteger(5);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Integer left = stock.decrementAndGet();
                if(left<1){
                    System.out.println("out of stock");
                    return;
                }
                System.out.println(Thread.currentThread().getName()+" gets one");
                System.out.println("left "+left);
            }).start();
        }
    }
}
