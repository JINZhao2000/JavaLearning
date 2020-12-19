package com.ayy.jdbctemplate.jdbcdaosupport;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @ ClassName JdbcDaoSupport
 * @ Description test for jdbc spring setting JdbcDAOSupport
 * @ Author Zhao JIN
 * @ Date 31/10/2020 01:42
 * @ Version 1.0
 */
public class JdbcDaoSupport {
    @Test
    public void test01(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("JDBCContext3.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        List<User> allUser = userDao.findAll();
        allUser.forEach(System.out::println);
    }
}
