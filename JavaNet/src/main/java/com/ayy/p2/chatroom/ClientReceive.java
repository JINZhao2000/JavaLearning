package com.ayy.p2.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @ ClassName ClientReceive
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/12/2020 10H
 * @ Version 1.0
 */
public class ClientReceive implements Runnable{
    private Socket socket;
    private DataInputStream dis;
    private boolean statut;

    public ClientReceive(Socket socket) {
        this.socket = socket;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.statut = true;
        } catch (IOException e) {
            System.out.println("Error in socket client");
            this.release();
        }
    }

    private String receive(){
        try {
            return dis.readUTF();
        } catch (IOException e) {
            System.out.println("Error in receive client");
            this.release();
        }
        return "";
    }

    private void release(){
        this.statut = false;
        ChatUtils.release(this.socket,this.dis);
    }

    @Override
    public void run() {
        while(statut){
            String msg = this.receive();
            if(!msg.equals("")){
                System.out.println(msg);
            }
        }
    }
}
