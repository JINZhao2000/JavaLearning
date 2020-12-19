package Cours3.XMLP;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testXMLP {

	@Test
	public void demo1() throws Exception {
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours3/XMLP/applicationContext.xml");
		Person person = applicationContext.getBean("person", Person.class);
		System.out.println(person);

	}
}
