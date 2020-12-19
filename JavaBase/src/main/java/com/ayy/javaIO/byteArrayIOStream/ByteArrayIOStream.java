package com.ayy.javaIO.byteArrayIOStream;

import java.io.*;

/**
 * @ ClassName ByteArrayIOStream
 * @ Description transformation between ByteArrayIOStream
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:35
 * @ Version 1.0
 */
public class ByteArrayIOStream {
    public static byte[] fileToByteArray (String filePath) {
        File src = new File(filePath);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(src);
            baos = new ByteArrayOutputStream();
            byte[] flush = new byte[1024*10];
            int len = -1;
            while ((len = is.read(flush))!=-1){
                baos.write(flush,0,len);
            }
            baos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baos==null?null:baos.toByteArray();
        }
    }

    public static void byteArrayToFile (byte[] src, String filePath) {
        File dest = new File(filePath);
        OutputStream os = null;
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(src);
            os = new FileOutputStream(dest);
            byte[] flush = new byte[1024*10];
            int len = -1;
            while((len = is.read(flush))!=-1){
                os.write(flush,0,len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
