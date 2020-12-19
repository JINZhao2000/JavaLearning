package com.ayy.threadlocals;

/**
 * @ ClassName InheritableThreadLocalTest
 * @ Description use of InheritableThread
 * @ Author Zhao JIN
 * @ Date 11/11/2020 23
 * @ Version 1.0
 */
public class InheritableThreadLocalTest {
    private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());
        threadLocal.set(2);
        // parent Thread of subThread is main
        Thread t = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());
            threadLocal.set(1);
            System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());
        });
        t.start();
        t.join();
        // inherit can't change the parent Thread
        System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());
    }
}
