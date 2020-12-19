package com.ayy.p1;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ ClassName URLDemo
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 22H
 * @ Version 1.0
 */
public class URLDemo {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://translate.google.com/?sl=en&tl=zh-CN&text=url&op=translate#a");
        System.out.println("protocol: "+url.getProtocol());
        System.out.println("ip: "+url.getHost());
        System.out.println("port: "+url.getPort()+ ", default port: "+url.getDefaultPort());
        System.out.println("resource file: "+url.getFile());
        System.out.println("resource path: "+url.getPath());
        System.out.println("params: "+ url.getQuery());
        System.out.println("anchor point: "+ url.getRef());
    }
}
