package Cours1.DI;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testDI {
	@Test
	public void demo01(){
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours1/DI/applicationContext.xml");
		BookService bookService = (BookService)applicationContext.getBean("bookService");
		bookService.addBook();
	}

//  unused
//	@Test
//	public void demo02(){
//		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
//		BookService bookService = (BookService) beanFactory.getBean("bookService");
//		bookService.addBook();
//	}
}
