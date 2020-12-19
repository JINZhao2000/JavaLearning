package com.ayy.javaIO;

import java.io.*;

/**
 * @ ClassName MyFileUtils
 * @ Description encapsulation of the utils of file operations
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:49
 * @ Version 1.0
 */
public class MyFileUtils {
    private MyFileUtils () {}

    public MyFileUtils getMyFileUtil(){
        if(this==null) {
            new MyFileUtils();
        }
        return this;
    }

    public static void copy (InputStream is, OutputStream os) {
        try{
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                os.write(flush, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(is,os);
        }
    }


    private static void close(AutoCloseable... clos){
        for (AutoCloseable c:clos) {
            try {
                if(c!=null){
                    c.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
