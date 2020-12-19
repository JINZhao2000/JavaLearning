package com.ayy.transaction.transfer.semi_auto;

import com.ayy.transaction.transfer.semi_auto.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ ClassName AccountServiceTest
 * @ Description test for accountService by proxy
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:28
 * @ Version 1.0
 */
public class AccountServiceTest {
    @Test
    public void demo01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext3.xml");
        AccountService accountService = applicationContext.getBean("proxyAccountService", AccountService.class);
        accountService.transfer("a","b",100);
    }
}
