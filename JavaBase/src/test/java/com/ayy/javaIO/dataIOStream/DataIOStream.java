package com.ayy.javaIO.dataIOStream;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName DataIOStream
 * @ Description test the uses of DataIOStream
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:54
 * @ Version 1.0
 */
public class DataIOStream {
    @Test
    public void test01() throws IOException {
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));

        dos.writeUTF("thisisUTF");
        dos.writeInt(18);
        dos.writeBoolean(false);
        dos.writeChar('a');
        // for new BufferedOutputStream
        dos.flush();

        data = baos.toByteArray();
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(data)));

        // required the same order as what we write in
        String msg = dis.readUTF();
        int a = dis.readInt();
        boolean tf = dis.readBoolean();
        char c = dis.readChar();

        System.out.println(msg+" "+a+" "+tf+" "+c);
    }
}
