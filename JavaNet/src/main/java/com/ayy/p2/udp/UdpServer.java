package com.ayy.p2.udp;

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
        DatagramSocket socket = new DatagramSocket(9999);
        byte[] container = new byte[1024];
        DatagramPacket packet = new DatagramPacket(container,0,container.length);
        while(true) {
            socket.receive(packet);
            byte[] data = packet.getData();
            int len = packet.getLength();
            String s = new String(data, 0, len);
            System.out.println(s);
            if(s.equals("quit")){
                break;
            }
        }
        socket.close();
    }
}
