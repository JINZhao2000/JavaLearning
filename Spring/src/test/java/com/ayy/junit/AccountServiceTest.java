package com.ayy.junit;

import com.ayy.junit.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ ClassName AccountServiceTest
 * @ Description Spring & Junit
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:28
 * @ Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:JunitContext1.xml")
public class AccountServiceTest {
    // The scan needn't be configured in xml
    @Autowired
    private AccountService accountService;

    @Test
    public void demo01(){
        accountService.transfer("a","b",100);
    }
}
