package Cours4.AnnotationScope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testAnnoScope {
	@Test
	public void demo1()throws Exception{
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours4/AnnotationScope/applicationContext.xml");
		UserService userService = applicationContext.getBean("userService", UserService.class);
		UserService userService2 = applicationContext.getBean("userService", UserService.class);
		System.out.println(userService);
		System.out.println(userService2);
		applicationContext.close();
	}
}
