package com.ayy.javaIO.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @ ClassName FileTest
 * @ Description use of APIs of File
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:40
 * @ Version 1.0
 */
public class FileTest {
    /**
     * use of APIs of File
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        File f = new File("./src/test/java/com/ayy/javaIO/file/a.txt");

        System.out.println(f);
        f.renameTo(new File("./src/test/java/com/ayy/javaIO/file/a.txt"));

        System.out.println(System.getProperty("user.dir"));

        File f2 = new File("./src/test/java/com/ayy/javaIO/file/c.txt");
        f2.createNewFile();

        System.out.println(f2.exists());
        System.out.println(f2.isDirectory());
        System.out.println(f2.isFile());
        System.out.println(new Date(f2.lastModified()));
        System.out.println(f2.length());
        System.out.println(f2.getName());
        System.out.println(f2.getPath());
        System.out.println(f2.getAbsolutePath());

        File f3 = new File("./src/test/java/com/ayy/javaIO/file/d.txt");
        // if there is a directory is not exist, the file won't be created
        boolean flag = f3.mkdir();
        // the file will be created anyway
        boolean flag2 = f3.mkdirs();
        System.out.println(flag);
        System.out.println(flag2);
    }
}
