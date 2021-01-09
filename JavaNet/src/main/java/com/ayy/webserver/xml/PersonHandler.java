package com.ayy.webserver.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @ ClassName PersonHandler
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/01/2021 21H
 * @ Version 1.0
 */
public class PersonHandler extends DefaultHandler {
    private List<Person> persons;
    private Person person;
    private String tag;
    @Override
    public void startDocument() throws SAXException {
        System.out.println("--- start ---");
        persons = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tag = qName;
        if (null != tag) {
            if(tag.equals("person")){
                person = new Person();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch,start,length).trim();
        if(null!=tag) {
            if (tag.equals("name")) {
                person.setName(content);
            } else if (tag.equals("age")) {
                if (content.length() > 0) {
                    person.setAge(Integer.parseInt(content));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(null!=qName) {
            if (qName.equals("person")) {
                persons.add(person);
            }
            tag = null;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("--- end ---");
    }

    public List<Person> getPersons() {
        return persons;
    }
}
