package com.ayy.threadlocals;

/**
 * @ ClassName ThreadLocalTest
 * @ Description test of LocalThread
 * @ Author Zhao JIN
 * @ Date 11/11/2020 22
 * @ Version 1.0
 */
public class ThreadLocalTest {
//    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>(){
//        @Override
//        protected Integer initialValue() {
//            return 200;
//        }
//    };
    // initial
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->200);
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());
        threadLocal.set(100);
        System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get());
        },"subThread").start();
    }

}
