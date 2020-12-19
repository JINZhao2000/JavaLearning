package com.ayy.javaIO.byteArrayIOStream;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName ByteArrayIOStreamTest
 * @ Description test uses of ByteArrayIOStream
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:34
 * @ Version 1.0
 */
public class ByteArrayIOStreamTest {
    /**
     * test for ByteArrayInputStream
     */
    @Test
    public void testInput(){
        byte[] src= "Les connards".getBytes();
        InputStream is = null;
        is = new ByteArrayInputStream(src);
        byte[] flush = new byte[5];
        int len = -1;
        try {
            while((len = is.read(flush))!=-1){
                String str = new String(flush,0,len);
                System.out.println(str);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * test for ByteArrayOutputStream
     */
    @Test
    public void testOutput(){
        byte[] dest= null;
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream();
        String msg = "Ils sont les connards";
        byte[] data = msg.getBytes();
        try {
            baos.write(data,0,data.length);
            baos.flush();
            dest = baos.toByteArray();
            System.out.println(dest.length+"-->"+new String(dest,0,baos.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * test the use of ByteArrayIOStream to copy a file
     */
    @Test
    public void testCopyByByteArrayIOStream(){
        byte[] data = ByteArrayIOStream.fileToByteArray(System.getProperty("user.dir")+"/src/main/resources/maki&nico.png");
        System.out.println(data.length);
        ByteArrayIOStream.byteArrayToFile(data,System.getProperty("user.dir")+"/src/test/result/maki&nico.png");
    }
}
