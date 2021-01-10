package com.ayy.webserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @ ClassName Request
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/01/2021 22H
 * @ Version 1.0
 */
public class Request {
    private String request;
    private String method;
    private String url;
    private String query;
    private final String CRLF = "\r\n";

    public Request(InputStream is){
        byte[] data = new byte[1024*1024];
        try {
            int len = is.read(data);
            this.request = new String(data,0,len);
        } catch (IOException e) {
            System.out.println("[FATAL] Information error");
            return;
        }
        parseRequest();
    }

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    private void parseRequest(){
        this.method = this.request.substring(0,this.request.indexOf(" "));
        this.url = this.request.substring(this.request.indexOf("/")+1,this.request.indexOf(" HTTP/"));
        if(this.url.indexOf("?")>=0){
            String[] urlArr = this.url.split("\\?");
            this.url = urlArr[0];
            this.query = urlArr[1];
        }
        if(query == null) {
            query = "";
        }
        if(method.equals("POST")){
            String body = this.request.substring(this.request.lastIndexOf(CRLF)).trim();
            if(query.equals("")) {
                query += body;
            }else{
                query+="&"+body;
            }
        }
        System.out.println(query);
    }
}
