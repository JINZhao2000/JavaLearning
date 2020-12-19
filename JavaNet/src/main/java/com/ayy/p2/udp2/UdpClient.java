package com.ayy.p2.udp2;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
        DatagramSocket socket = new DatagramSocket(10001);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));
        dos.writeUTF("text de test");
        dos.writeInt(22);
        dos.writeBoolean(false);
        dos.writeChar('a');
        dos.flush();
        byte[] data = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(data,0,data.length,new InetSocketAddress("127.0.0.1",10000));
        socket.send(packet);
        socket.close();
    }
}
