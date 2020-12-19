package com.ayy.javaIO.file;

import org.junit.Test;

import java.io.File;

/**
 * @ ClassName FileAndDirTest
 * @ Description use of APIs of files and directories
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:59
 * @ Version 1.0
 */
public class FileAndDirTest {
    /**
     * use of separator
     */
    @Test
    public void testSeparator(){
        String path = ".src\\JAVAIO";
        String path2 = ".src/JAVAIO";
        String path3 = ".src"+ File.separator+"JAVAIO";
        System.out.println(path);
        System.out.println(path2);
        System.out.println(path3);
    }

    /**
     * three different ways to create a file
     */
    @Test
    public void testCreateFile(){
        String path = ".src"+ File.separator+"JAVAIO";

        File src = new File(path);
        System.out.println(src.length());

        File src2 = new File(".src","JAVAIO");
        System.out.println(src2.length());

        File src3 = new File(new File(".src"),"JAVAIO");
        System.out.println(src3.length());
    }

    /**
     * get absolute path
     */
    @Test
    public void testGetPath(){
        String path = ".src"+ File.separator+"JAVAIO";

        File src = new File(path);
        System.out.println(src.getAbsolutePath());

        File src2 = new File("JAVAIO");
        System.out.println(src2.getAbsolutePath());
    }

    /**
     * Creation and Delete of a directory
     */
    @Test
    public void testCreateDeleteDirectory(){
        File dir = new File(".src/JAVAIO/test01");
        boolean flag = dir.mkdir();
        System.out.println(flag);
        File dir2 = new File(".src/JAVAIO/test02/01");
        boolean flag2 = dir2.mkdir();
        System.out.println(flag2);
        File dir3 = new File(".src/JAVAIO/test02/01");
        boolean flag3 = dir3.mkdirs();
        System.out.println(flag3);
        dir.delete();
        dir3.delete();
        new File(".src/JAVAIO/test02").delete();
    }

    /**
     * print the subdirectory and the sub file and the root list
     */
    @Test
    public void testPathDirectory(){
        File dir = new File(".src/JAVAIO/");
        String[] subnames = dir.list();
        for (String s : subnames) {
            System.out.println(s);
        }
        System.out.println("------------------------");
        File[] subfiles = dir.listFiles();
        for (File f : subfiles) {
            System.out.println(f.getName());
        }
        System.out.println("------------------------");
        File[] roots = dir.listRoots();
        for (File f : roots) {
            System.out.println(f.getAbsolutePath());
        }
    }

    /**
     * print a file tree
     */
    @Test
    public void testPrintFileTree(){
        File src = new File("./src");
        printName(src,0);
    }

    public static void printName(File f, int deep){
        for (int i = 0; i <deep; i++) {
            System.out.print("-");
        }
        System.out.println(f.getName());
        if(null == f || !f.exists()){
            return;
        }else if(f.isDirectory()){
            for(File f1:f.listFiles()){
                printName(f1,deep+1);
            }
        }
    }

    /**
     * count the number of files
     */
    @Test
    public void testFileNumber(){
        File src = new File("./src");
        System.out.println(count(src));

    }

    public static long count (File f) {
        long len=0;
        if (null != f && f.exists()) {
            if (f.isFile()) {
                len += f.length();
            } else {
                for (File f1 : f.listFiles()) {
                    count(f1);
                }
            }
        }
        return len;
    }
}
