package com.ayy.javaIO.fileIOStream;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName IOStreamTest
 * @ Description test the uses of FileIOStream
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:02
 * @ Version 1.0
 */
public class FileIOStreamTest {
    /**
     * test FileInputStream character by character
     */
    @Test
    public void test01(){
        File src = new File(System.getProperty("user.dir")+"/src/main/resources/FileIOStream.txt");
        InputStream is =null;
        try {
            is = new FileInputStream(src);
            int temp;
            while ((temp=is.read())!=-1){
                System.out.print(temp+":");
                System.out.println((char)temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * test FileInputStream2
     */
    @Test
    public void test02(){
        File src = new File(System.getProperty("user.dir")+"/src/main/resources/FileIOStream.txt");
        InputStream is =null;
        try {
            is = new FileInputStream(src);
            byte[] flush = new byte[1024*10];//10kb
            int len = -1;
            while ((len=is.read(flush))!=-1){
                String str = new String(flush,0,len);
                System.out.print(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * test FileOutputStream
     */
    @Test
    public void test03(){
        File dest = new File(System.getProperty("user.dir")+"/src/main/resources/FileIOStream.txt");
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest/*,true*/);
            String msg = "FileOutputStreamTest\r\n";
            byte[] data = msg.getBytes();
            os.write(data);
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * File copy by FileIOStream
     */
    @Test
    public void test04(){
        File src = new File(System.getProperty("user.dir")+"/src/main/resources/FileIOStream.txt");
        File dest = new File(System.getProperty("user.dir")+"/src/test/result/FileIOStream.txt");
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest,true);

            byte[] flush = new byte[1024];
            int len = -1;
            while((len = is.read(flush))!=-1){
                os.write(flush,0,len);
                os.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null!=is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * test of encapsulation of copy
     */
    @Test
    public void test05(){
        FileIOStream.copy(System.getProperty("user.dir")+"/src/main/resources/FileIOStream.txt",
                System.getProperty("user.dir")+"/src/test/result/FileIOStream.txt");
    }
}
