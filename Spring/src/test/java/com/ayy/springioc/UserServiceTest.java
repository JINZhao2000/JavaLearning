package com.ayy.springioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {
    @Test
    public void demo01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Bean1.xml");
        // UserService userService = new UserServiceImpl();
        UserService userService = applicationContext.getBean("userServiceId", UserService.class);
        userService.addUser();
    }
}
