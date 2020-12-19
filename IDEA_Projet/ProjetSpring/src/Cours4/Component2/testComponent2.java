package Cours4.Component2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class testComponent2 {
	@Test
	public void demo01() {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours4/Component2/applicationContext.xml");
		StudentAction studentAction = applicationContext.getBean("studentAction", StudentAction.class);
		studentAction.execute();
	}
}
