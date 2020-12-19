package com.ayy.p2.udp4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @ ClassName ReceiveMsg
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 12H
 * @ Version 1.0
 */
public class ReceiveMsg implements Runnable{
    private DatagramSocket socket;
    private byte[] container = new byte[1024];
    private DatagramPacket packet;
    private byte[] data;
    private int len;
    public ReceiveMsg(int destPort) {
        try {
            this.socket = new DatagramSocket(destPort);
            this.packet = new DatagramPacket(container,0,container.length);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Conversation Started");
        try {
            while (true){
                socket.receive(packet);
                data = packet.getData();
                len = packet.getLength();
                String msg = new String(data,0,len);
                System.out.println("other : "+msg);
                if(msg.equals("quit")){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
