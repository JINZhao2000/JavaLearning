package com.ayy;

import com.ayy.bean.Books;
import com.ayy.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/03/2021
 * @ Version 1.0
 */
public class AllTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        BookService bookServiceImpl = (BookService) context.getBean("bookServiceImpl");
        System.out.println(bookServiceImpl);
        List<Books> books = bookServiceImpl.queryAllBooks();
        books.forEach(System.out::println);
    }
}
