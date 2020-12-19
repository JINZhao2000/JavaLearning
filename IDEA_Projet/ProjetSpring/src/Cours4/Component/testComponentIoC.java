package Cours4.Component;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class testComponentIoC {
	@Test
	public void demo01() {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours4/Component/applicationContext.xml");
		UserService userService = applicationContext.getBean("userService",UserService.class);
		userService.addUser();
	}
}
