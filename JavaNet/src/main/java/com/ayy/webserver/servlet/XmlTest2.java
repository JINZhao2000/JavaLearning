package com.ayy.webserver.servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

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
        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),handler);
        List<Entity> entities = handler.getEntities();
        List<Mapping> mappings = handler.getMappings();
        entities.forEach(System.out::println);
        mappings.forEach(System.out::println);
    }
}
