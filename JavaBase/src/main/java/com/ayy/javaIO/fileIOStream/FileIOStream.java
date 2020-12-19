package com.ayy.javaIO.fileIOStream;

import java.io.*;

/**
 * @ ClassName fileIOStream
 * @ Description encapsulation of copy
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:11
 * @ Version 1.0
 */
public class FileIOStream {
    public static void copy (String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest, true);

            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                os.write(flush, 0, len);
                os.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
