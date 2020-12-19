package com.ayy.javaIO.bufferedIORWFile;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName BufferedIORWFile
 * @ Description test the uses of Buffered IO RW for Files
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:22
 * @ Version 1.0
 */
public class BufferedIORWFile {
    /**
     * test for BufferedInputStream
     */
    @Test
    public void test01(){
        File src = new File(System.getProperty("user.dir")+ "/src/main/resources/BufferedIORW.txt");
        InputStream is = null;
        try{
            is = new BufferedInputStream(new FileInputStream(src));
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = is.read(flush))!=-1){
                String str = new String(flush,0,len);
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=is){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * test for BufferedOutputStream
     */
    @Test
    public void test02(){
        File dest = new File(System.getProperty("user.dir")+ "/src/test/result/BufferedIORW.txt");
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(dest));
            String msg = "BufferedIOStreamTest";
            byte[] data = msg.getBytes();
            os.write(data,0,data.length);
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=os){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * test for BufferedReader
     */
    @Test
    public void test03(){
        File src = new File(System.getProperty("user.dir")+ "/src/main/resources/BufferedIORW.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(src));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=reader){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * test for BufferedWriter
     */
    @Test
    public void test04(){
        File dest = new File(System.getProperty("user.dir")+ "/src/test/result/BufferedIORW.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(dest));
            writer.append("Buffered").append("IO");
            writer.newLine();
            writer.append("RW").append("Test");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=writer){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
