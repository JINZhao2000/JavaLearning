package com.ayy.test;

import com.ayy.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountTest {
	@Test
	public void accountTest(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
		AccountDao accountDao = applicationContext.getBean(AccountDao.class);
		accountDao.FindAll();
	}
}
