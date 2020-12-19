package Cours3.XMLConstructor;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testXMLConstructor {

	@Test
	public void demo1() throws Exception {
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours3/XMLConstructor/applicationContext.xml");
		User user= applicationContext.getBean("user", User.class);
		System.out.println(user);

		applicationContext.close();
		//applicationContext.getClass().getMethod("close").invoke(applicationContext);
		//this method is not defined in the interface, but in realisation
	}
}
