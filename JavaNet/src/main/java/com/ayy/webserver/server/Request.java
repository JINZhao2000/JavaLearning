package com.ayy.webserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

/**
 * @ ClassName Request
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/01/2021 22H
 * @ Version 1.0
 */
public class Request {
    private final String CRLF = "\r\n";
    private String request;
    private String method;
    private String url;
    private String query;
    private Map<String, List<String>> parameters;

    public Request(InputStream is){
        parameters = new HashMap<>();
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
        if(this.url.contains("?")){
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
        convertMap();
    }

    private void convertMap(){
        String[] keyValues = this.query.split("&");
        for(String s:keyValues){
            String[] kv = s.split("=");
            kv = Arrays.copyOf(kv,2);
            String key = kv[0];
            String value = kv[1]==null?null:decode(kv[1], "UTF-8");
            if(!parameters.containsKey(key)){
                parameters.put(key,new ArrayList<>());
            }
            parameters.get(key).add(value);
        }
    }

    public String[] getParametersValues(String key){
        List<String> valueList = this.parameters.get(key);
        if(null==valueList||valueList.size()==0){
            return null;
        }
        return valueList.toArray(new String[0]);
    }

    public String getParametersValue(String key){
        String values[] = this.getParametersValues(key);
        return values==null?null:values[0];
    }

    private String decode(String value,String encoding){
        try {
            return java.net.URLDecoder.decode(value,encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println("[FATAL] Encoding error");
        }
        return null;
    }

    public String getUrl() {
        return url;
    }
}
