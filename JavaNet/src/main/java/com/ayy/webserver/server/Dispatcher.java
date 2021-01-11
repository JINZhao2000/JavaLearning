package com.ayy.webserver.server;

import java.io.*;
import java.net.Socket;

/**
 * @ ClassName Dispatcher
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */
public class Dispatcher implements Runnable {
    private Socket clientSocket;
    private Request request;
    private Response response;

    public Dispatcher(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            request = new Request(clientSocket);
            response = new Response(clientSocket);
        } catch (IOException e) {
            try {
                clientSocket.close();
            } catch (IOException ioException) {
                System.out.println("[WARN] The server is not closed properly");
            }
        }
    }

    @Override
    public void run() {
        try {
            if(null==request.getUrl()||request.getUrl().equals("")){
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                byte[] data = new byte[1024 * 1024];
                int len = is.read(data);
                response.add(new String(data, 0, len));
                response.push(200);
                is.close();
                return;
            }
            Servlet servlet = WebApp.getServletFormURL(request.getUrl());
            if (null != servlet) {
                servlet.service(request, response);
                response.push(200);
            } else {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                byte[] data = new byte[1024 * 1024];
                int len = is.read(data);
                response.add(new String(data, 0, len));
                response.push(404);
                is.close();
            }
        } catch (Exception e) {
            try {
                response.push(505);
            } catch (IOException ioException) {
                System.out.println("[FATAL] Unknown error");
            }
        } finally {
            response = null;
            request = null;
            clientSocket = null;
        }
    }
}
