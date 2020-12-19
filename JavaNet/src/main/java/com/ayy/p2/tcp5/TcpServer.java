package com.ayy.p2.tcp5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ ClassName TcpServer
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 20H
 * @ Version 1.0
 */
public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(10000);
        boolean flag = true;
        while (flag){
            Socket socket = server.accept();
            new Thread(new Channel(socket)).start();
        }
        server.close();
    }
}

class Channel implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    public Channel(Socket socket) {
        this.socket = socket;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            try {
                this.socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String[] data = this.receive().split("&");
            String name = data[0];
            String pwd = data[1];
            if (name.equals("abc") && pwd.equals("1111")) {
                send("Login Success");
            } else {
                send("Login Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.release(socket,dis,dos);
        }
    }

    private String receive(){
        String msg = "";
        try {
            return dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private void send(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void release(AutoCloseable... acs){
        for (AutoCloseable ac : acs){
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}