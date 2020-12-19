package com.ayy.synchronization.order.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ ClassName HelloWorld
 * @ Description use of hellojob
 * @ Author Zhao JIN
 * @ Date 11/11/2020 21
 * @ Version 1.0
 */
public class HelloJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    public HelloJob() {}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Hello World "+new Date());
    }
}
