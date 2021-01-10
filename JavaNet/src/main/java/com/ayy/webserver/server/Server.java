package com.ayy.webserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ ClassName Server
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/01/2021 21H
 * @ Version 1.0
 */
public class Server {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.receive();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            System.out.println("[FATAL] Can't start server");
        }
    }
    public void receive(){
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[INFO] A client is connected");
            Request request = new Request(clientSocket);
            
            Response response = new Response(clientSocket);
            response.add("<html>");
            response.add("<head>");
            response.add("<title>");
            response.add("Response success");
            response.add("</title>");
            response.add("</head>");
            response.add("<body>");
            response.add("Welcome");
            response.add("</body>");
            response.add("</html>");
            response.push(200);
        } catch (IOException e) {
            System.out.println("[FATAL] A client can't connect to this server");
        }
    }
    public void stop(){}
}
