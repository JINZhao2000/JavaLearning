package com.ayy.createthread.implcallable;

import java.util.concurrent.*;

/**
 * @ ClassName StartThreadTest
 * @ Description the run of thread decided by cpu
 * @ Author Zhao JIN
 * @ Date 09/11/2020 15:31
 * @ Version 1.0
 */
public class StartThreadTest implements Callable<Boolean> {

    public static void main (String[] args) throws InterruptedException, ExecutionException {
        StartThreadTest st = new StartThreadTest();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> booleanFuture = executorService.submit(st);
        for (int i = 0; i < 20; i++) {
            System.out.println("Thread_main");
        }
        boolean result = booleanFuture.get();
        executorService.shutdownNow();
    }

    @Override
    public Boolean call () throws Exception {
        for (int i = 0; i <20; i++) {
            System.out.println("Thread_class");
        }
        return true;
    }
}
