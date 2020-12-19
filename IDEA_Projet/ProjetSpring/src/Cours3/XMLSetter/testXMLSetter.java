package Cours3.XMLSetter;

import Cours3.XMLConstructor.User;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testXMLSetter {

	@Test
	public void demo1() throws Exception {
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours3/XMLSetter/applicationContext.xml");
		Person person = applicationContext.getBean("person",Person.class);
		System.out.println(person);

	}
}
