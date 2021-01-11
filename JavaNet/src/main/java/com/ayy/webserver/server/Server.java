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
    private boolean isRunning;
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.receive();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
            isRunning = true;
        } catch (IOException e) {
            System.out.println("[FATAL] Can't start server");
            this.stop();
        }
    }
    public void receive(){
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[INFO] A client is connected");
            while (isRunning) {
                new Thread(new Dispatcher(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("[FATAL] A client can't connect to this server");
        }
    }
    public void stop(){
        isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("[WARN] Server close failed");
        }
    }
}
