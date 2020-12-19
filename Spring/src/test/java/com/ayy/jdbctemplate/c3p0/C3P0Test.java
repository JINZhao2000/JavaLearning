package com.ayy.jdbctemplate.c3p0;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @ ClassName C3P0Test
 * @ Description test for jdbc spring setting C3P0
 * @ Author Zhao JIN
 * @ Date 30/10/2020 21:19
 * @ Version 1.0
 */
public class C3P0Test {
    @Test
    public void test01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext2.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        List<User> allUser = userDao.findAll();
        allUser.forEach(System.out::println);
    }
}
