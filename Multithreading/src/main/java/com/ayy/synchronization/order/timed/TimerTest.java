package com.ayy.synchronization.order.timed;

import java.util.*;

/**
 * @ ClassName TimerTest
 * @ Description time scheduling
 * @ Author Zhao JIN
 * @ Date 11/11/2020 21
 * @ Version 1.0
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
//        timer.schedule(new MyTask(),2000);
//        timer.schedule(new MyTask(),2000,1000);
//        Month [0,11]
        Calendar calendar = new GregorianCalendar(2020,10,11,21,13,00);
        System.out.println(calendar.getTime());
        // task begin_time period
        timer.schedule(new MyTask(),calendar.getTime(),200);
    }
}

class MyTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("---begin---");
        for (int i = 0; i < 10; i++) {
            System.out.println("run of timertask"+i);
        }
        System.out.println("---end---");
    }
}