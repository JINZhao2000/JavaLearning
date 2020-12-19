package Cours1.StaticFactory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testStaticFactory {
	@Test
	public void demo1(){
		UserService2 userService = MyBeanFactory.createService();
		userService.addUser();
	}

	@Test
	public void demo2(){
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours1/StaticFactory/applicationContext.xml");
		UserService2 userService = applicationContext.getBean("userService", UserService2.class);
		userService.addUser();
	}
}
