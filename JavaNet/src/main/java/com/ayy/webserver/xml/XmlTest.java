package com.ayy.webserver.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

/**
 * @ ClassName XmlTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/01/2021 21H
 * @ Version 1.0
 */
public class XmlTest {
    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        PersonHandler handler = new PersonHandler();
        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("persons.xml"),handler);
        List<Person> persons = handler.getPersons();
        persons.forEach(System.out::println);
    }
}