package com.ayy.jvm.loader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ ClassName ChiffreUtil
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/01/2021 20H
 * @ Version 1.0
 */
public class ChiffreUtil {
    public static void main(String[] args) {
        chiffre("D:/JavassistTest/com/ayy/ref/uselib/A.class","D:/JavassistTest/temp/com/ayy/ref/uselib/A.class");
    }

    public static void chiffre(String src, String dest){
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try{
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            int temp = -1;
            while((temp = fis.read())!=-1){
                fos.write(temp^0xff);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
