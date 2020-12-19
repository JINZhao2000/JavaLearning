package com.ayy.p2.chatroom;

import java.io.*;
import java.net.Socket;

/**
 * @ ClassName ClientSend
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/12/2020 10H
 * @ Version 1.0
 */
public class ClientSend implements Runnable{
    private Socket socket;
    private BufferedReader console;
    private DataOutputStream dos;
    private boolean statut;
    private String name;

    public ClientSend(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        this.console = new BufferedReader(new InputStreamReader(System.in));
        try {
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.send(name);
            this.statut = true;
        } catch (IOException e) {
            System.out.println("Error in socket client");
            this.release();
        }
    }

    private void release(){
        this.statut = false;
        ChatUtils.release(this.socket,this.console,this.dos);
    }

    private String getMsg(){
        try {
            return console.readLine();
        } catch (IOException e) {
            System.out.println("Error in console client");
            this.release();
        }
        return "";
    }

    private void send(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("Error in send client");
            this.release();
        }
    }

    @Override
    public void run() {
        while(statut){
            String msg = this.getMsg();
            if(!msg.equals("")){
                this.send(msg);
            }
        }
    }
}
