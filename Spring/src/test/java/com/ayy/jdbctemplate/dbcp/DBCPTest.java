package com.ayy.jdbctemplate.dbcp;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ ClassName DBCPTest
 * @ Description test for jdbc spring setting DBCP
 * @ Author Zhao JIN
 * @ Date 30/10/2020 21:01
 * @ Version 1.0
 */
public class DBCPTest {
    @Test
    public void test01(){
        User user = new User();
        user.setId(4);
        user.setUsername("d");
        user.setPassword("1234");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext1.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
    }
}
