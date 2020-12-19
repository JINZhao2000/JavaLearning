package Cours3.XMLCollection;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testXMLCollection {

	@Test
	public void demo1() throws Exception {
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours3/XMLCollection/applicationContext.xml");
		CollectionData collectionData= applicationContext.getBean("collectionData", CollectionData.class);
		System.out.println(collectionData);
	}
}
