package Cours2.Cycle;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class testCycle {

	@Test
	public void demo1() throws Exception {
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours2/Cycle/applicationContext.xml");
		UserService userService = applicationContext.getBean("userService",UserService.class);
		userService.addUser();

		applicationContext.close();
		//applicationContext.getClass().getMethod("close").invoke(applicationContext);
		//this method is not defined in the interface, but in realisation
	}
}
