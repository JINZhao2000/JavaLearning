package com.ayy.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/04/2021
 * @ Version 1.0
 */
@Service
public class ScheduledService {
    // sec min hour day month dayofweek
    @Scheduled(cron = "0 * * * * 0-7")
    public void hello(){
        System.out.println("hello");
    }
}
