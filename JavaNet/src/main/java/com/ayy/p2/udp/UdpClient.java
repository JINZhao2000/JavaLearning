package com.ayy.p2.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @ ClassName UdpClient
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 10H
 * @ Version 1.0
 */
public class UdpClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(8888);
        while(true){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String msg = reader.readLine();
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data,0,data.length,new InetSocketAddress("127.0.0.1",9999));
            socket.send(packet);
            if(msg.equals("quit")){
                break;
            }
        }
        socket.close();
    }
}
