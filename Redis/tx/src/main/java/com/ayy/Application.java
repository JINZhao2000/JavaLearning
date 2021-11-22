package com.ayy;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Zhao JIN
 */

public class Application {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final CountDownLatch cdl = new CountDownLatch(64);
        final List<Future<Boolean>> results = new LinkedList<>();
        BlockingQueue<Runnable> bq = new ArrayBlockingQueue<>(128);
        ThreadPoolExecutor thPool = new ThreadPoolExecutor(64, 64, 20L, TimeUnit.SECONDS, bq, Executors.defaultThreadFactory(),  new ThreadPoolExecutor.AbortPolicy());
        List<Stimulator> stimulatorList = new LinkedList<>();
        for (int i = 0; i < 64; i++) {
            stimulatorList.add(new Stimulator("user"+i, "productId", cdl));
        }
        long begin = System.currentTimeMillis();
        for (Stimulator stimulator : stimulatorList) {
            results.add(thPool.submit(stimulator));
        }
        cdl.await();
        Thread.sleep(2000);
        thPool.shutdown();
        int sum = 0;
        for (Future<Boolean> result : results) {
            if (result.get()) {
                sum ++ ;
            }
        }
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(sum);
    }
}
