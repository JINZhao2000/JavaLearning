package com.ayy.p2.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * @ ClassName Server
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/12/2020 09H
 * @ Version 1.0
 */
public class Server {
    private static List<Channel> channelList = new Vector<>();
    public static void main(String[] args) throws Exception {
        System.out.println("--- Server Start ---");
        ServerSocket server = new ServerSocket(10000);
        boolean flag = true;
        while(flag) {
            Socket socket = server.accept();
            System.out.println("--- A Client Connected ---");
            Channel c = new Channel(socket);
            channelList.add(c);
            new Thread(c).start();
        }
        server.close();
    }

    static class Channel implements Runnable{
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private Socket socket;
        private boolean statut;
        private String name;

        public Channel(Socket socket) {
            this.socket = socket;
            try {
                this.dis = new DataInputStream(socket.getInputStream());
                this.dos = new DataOutputStream(socket.getOutputStream());
                this.statut = true;
                this.name = receive();
                this.send("Welcome in the chatroom");
                this.sendToOthers(this.name+" has joined this chatroom");
            } catch (IOException e) {
                System.out.println("Error in socket");
                this.release();
            }
        }

        private String receive(){
            String msg = "";
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                System.out.println(this.name+" left");
                this.release();
            }
            return msg;
        };
        private void send(String msg){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("Error in write");
                this.release();
            }
        };

        private void sendToOthers(String msg){
            boolean isPrivate = msg.startsWith("@");
            if(isPrivate) {
                String dest = msg.substring(1,msg.indexOf(":"));
                for (Channel c : channelList){
                    if(c.getName().equals(dest)){
                        c.send("Private msg from "+this.name+" : "+msg.substring(msg.indexOf(":")+1,msg.length()));
                    }
                }
            }else{
                for (Channel c : channelList) {
                    if (c == this) {
                        continue;
                    } else {
                        c.send(this.name + " : " + msg);
                    }
                }
            }
        }

        public String getName() {
            return name;
        }

        private void release(){
            this.statut = false;
            channelList.remove(this);
            ChatUtils.release(this.socket,this.dis,this.dos);
        }

        @Override
        public void run() {
            while(statut){
                String msg = receive();
                if(!msg.equals("")){
                    sendToOthers(msg);
                }
            }
        }
    }
}
