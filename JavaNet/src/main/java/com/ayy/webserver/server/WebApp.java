package com.ayy.webserver.server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @ ClassName WebApp
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */
public class WebApp {
    private static WebContent content;
    static {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            WebHandler handler = new WebHandler();
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web2.xml"), handler);
            content = new WebContent(handler.getEntities(), handler.getMappings());

        } catch (Exception e) {
            System.out.println("[FATAL] Error of source file");
        }
    }

    public static Servlet getServletFormURL(String url){
        String className = content.getClassName("/"+url);
        if (className == null){
            System.out.println("404 Not Found");
        }else {
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                Servlet servlet = (Servlet) clz.getDeclaredConstructor().newInstance();
                return servlet;
            } catch (Exception e) {
                System.out.println("[FATAL] Url error");
            }
        }
        return null;
    }
}
