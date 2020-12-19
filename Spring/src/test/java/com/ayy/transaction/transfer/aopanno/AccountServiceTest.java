package com.ayy.transaction.transfer.aopanno;

import com.ayy.transaction.transfer.aopanno.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ ClassName AccountServiceTest
 * @ Description test for accountService by aop annotation
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:28
 * @ Version 1.0
 */
public class AccountServiceTest {
    @Test
    public void demo01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext5.xml");
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        accountService.transfer("a","b",100);
    }
}
