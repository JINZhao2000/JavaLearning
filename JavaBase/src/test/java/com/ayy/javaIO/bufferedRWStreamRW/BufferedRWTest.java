package com.ayy.javaIO.bufferedRWStreamRW;

import org.junit.Test;

import java.io.*;
import java.net.URL;

/**
 * @ ClassName BufferedRWTest
 * @ Description test the uses of BufferedRW for Streams
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:17
 * @ Version 1.0
 */
public class BufferedRWTest {
    @Test
    public void testInputInConsoleAndOutput(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String msg = "";
            while(!msg.equals("exit")){
                msg = reader.readLine();
                if(!msg.equals("exit")) {
                    writer.write(msg);
                    writer.newLine();
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadAFileAndOuputInFile(){
        try(BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new URL("http://www.google.fr").openStream(),"UTF-8"));
            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(System.getProperty("user.dir")+ "/src/test/result/google.html"),"UTF-8"));)
        {
            String line;
            while(null != (line = reader.readLine())){
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
