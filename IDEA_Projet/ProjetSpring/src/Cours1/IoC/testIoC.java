package Cours1.IoC;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class testIoC {
	@Test
	public void demo01(){
		UserService userService = new UserServiceImpl();
		userService.addUser();
	}

	@Test
	public void demo02(){
		//get from spring
		//get container
		ApplicationContext applicationContext =
				new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours1/IoC/applicationContext.xml");
		//get context    no need to new
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.addUser();
	}
}
