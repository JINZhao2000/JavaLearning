package com.ayy.p2.udp3;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @ ClassName UdpServer
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 10H
 * @ Version 1.0
 */
public class UdpServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(10000);
        byte[] container = new byte[1024];
        DatagramPacket packet = new DatagramPacket(container,0,container.length);
        while(true) {
            socket.receive(packet);
            byte[] data = packet.getData();
            int len = packet.getLength();
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(data,0,len)));
            User user = (User) ois.readObject();
            System.out.println(user);
            if(len==10){
                break;
            }
        }
        socket.close();
    }
}
