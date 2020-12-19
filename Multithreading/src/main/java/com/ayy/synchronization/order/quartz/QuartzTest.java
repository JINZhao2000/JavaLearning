package com.ayy.synchronization.order.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @ ClassName QuartzSample
 * @ Description test of Quartz
 * @ Author Zhao JIN
 * @ Date 11/11/2020 22
 * @ Version 1.0
 */
public class QuartzTest {
    public void run() throws Exception{
        Logger log = LoggerFactory.getLogger(QuartzTest.class);
        log.info("------= Initializing -------");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        log.info("--- Initializing Complete ---");
        Date runTime = evenMinuteDate(new Date());
        log.info("------ Scheduling Job ------");
        JobDetail job = newJob(HelloJob.class).withIdentity("job1","group1").build();
        Trigger trigger = newTrigger().withIdentity("trigger1","group1").startAt(runTime).build();
        scheduler.scheduleJob(job,trigger);
        log.info(job.getKey()+" will run at "+runTime);
        scheduler.start();
        log.info("----- Started Scheduler -----");
        log.info("----- Waiting 6 seconds -----");
        try{
            Thread.sleep(6000);
        } catch (InterruptedException e) {}
        log.info("------- Shuting down -------");
        scheduler.shutdown(true);
        log.info("----- Shutdown Complete -----");
    }

    public static void main(String[] args) throws Exception {
        QuartzTest test = new QuartzTest();
        test.run();
    }
}
