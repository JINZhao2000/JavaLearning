package com.ayy.webserver.servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.lang.reflect.Method;

/**
 * @ ClassName XmlTest2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/01/2021 22H
 * @ Version 1.0
 */
public class XmlTest2 {
    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        WebHandler handler = new WebHandler();
        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), handler);
        WebContent content = new WebContent(handler.getEntities(), handler.getMappings());
        String className = content.getClassName("/login");
        if (className == null){
            System.out.println("404 Not Found");
        }else {
            Class<?> clz = (Class<Servlet>) Class.forName(className);
            Servlet servlet = (Servlet) clz.getDeclaredConstructor().newInstance();
            Method m = clz.getMethod("service");
            m.invoke(servlet);
        }
    }
}
