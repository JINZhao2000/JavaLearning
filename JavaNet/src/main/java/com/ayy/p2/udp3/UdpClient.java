package com.ayy.p2.udp3;

import java.io.*;
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
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
        oos.writeObject(new User("testName"));
        oos.flush();
        byte[] data = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(data,0,data.length,new InetSocketAddress("127.0.0.1",10000));
        socket.send(packet);
        socket.close();
    }
}

class User implements Serializable{
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{ name :" + name + " }";
    }
}
