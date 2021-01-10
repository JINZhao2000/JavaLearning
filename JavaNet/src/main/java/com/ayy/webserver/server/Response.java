package com.ayy.webserver.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @ ClassName Response
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/01/2021 22H
 * @ Version 1.0
 */
public class Response {
    private BufferedWriter writer;
    private StringBuilder content;
    private StringBuilder header;
    private int lenContent;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    private Response() {
        content = new StringBuilder();
        header = new StringBuilder();
        lenContent = 0;
    }

    public Response(Socket client){
        this();
        try {
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            System.out.println("[FATAL] Client's output stream error");
            header = null;
        }
    }

    public Response(OutputStream os){
        this();
        writer = new BufferedWriter(new OutputStreamWriter(os));
    }

    public Response add(String message){
        content.append(message);
        lenContent += message.getBytes().length;
        return this;
    }

    public Response addAndWrap(String message){
        content.append(message).append(CRLF);
        lenContent += (message+CRLF).getBytes().length;
        return this;
    }

    // response line : HTTP/1.1 200 OK
    // header
    /* Date: Mon,11JAN202100:00:00GMT
     * Server:MyServer Server/0.0.1;charset=UTF-8
     * Content-type:text/html
     * Content-length:(length of byte)
     * */
    // empty line
    // body
    private void createHeader(int code){
        header.append("HTTP/1.1").append(BLANK);
        switch (code){
            case 200:
                header.append("200").append(BLANK).append("OK").append(CRLF);
                break;
            case 404:
                header.append("404").append(BLANK).append("NOT FOUND").append(CRLF);
                break;
            case 505:
                header.append("505").append(BLANK).append("SERVER ERROR").append(CRLF);
                break;
            default:
                throw new IllegalArgumentException("Invalid code");
        }

        header.append("Date:").append(new Date()).append(CRLF);
        header.append("Server:MyServer Server/0.0.1;charset=UTF-8").append(CRLF);
        header.append("Content-type:text/html").append(CRLF);
        header.append("Content-length:").append(lenContent).append(CRLF);
        header.append(CRLF);
    }

    public void push(int code) throws IOException {
        if(null==header){
            code = 505;
        }
        createHeader(code);
        writer.append(header);
        writer.append(content);
        writer.flush();
    }
}
