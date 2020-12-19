package com.ayy.jdbctemplate.properties;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ ClassName PropertiesTest
 * @ Description test for jdbc spring setting properties
 * @ Author Zhao JIN
 * @ Date 31/10/2020 02:03
 * @ Version 1.0
 */
public class PropertiesTest {
    @Test
    public void test01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext4.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        User user = userDao.getById(1);
        System.out.println(user);
    }
}
