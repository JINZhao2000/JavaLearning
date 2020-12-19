package com.ayy.javaIO.fileRW;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName FileRWTest
 * @ Description test the uses of FileRW
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:49
 * @ Version 1.0
 */
public class FileRWTest {
    /**
     * FileReaderTest
     */
    @Test
    public void testFileReader(){
        File src = new File(System.getProperty("user.dir")+"/src/main/resources/FileReader.txt");

        Reader ir = null;
        try {
            ir = new FileReader(src);
            char[] flush = new char[1024];
            int len = -1;
            while((len = ir.read(flush))!=-1){
                String str = new String(flush,0,len);
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=ir) {
                    ir.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * FileWriterTest
     */
    public void testFileWriter(){
        File dest = new File(System.getProperty("user.dir")+"FileWriter.txt");

        Writer iw = null;
        try {
            iw = new FileWriter(dest,true);
            String msg = "FileWriterTest\r\n";
            char[] data = msg.toCharArray();
            iw.write(data,0 ,data.length);
            iw.flush();
            iw.append(msg).append(msg);
            iw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=iw) {
                    iw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
