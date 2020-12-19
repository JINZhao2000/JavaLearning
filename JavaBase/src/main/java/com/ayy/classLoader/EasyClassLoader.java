package com.ayy.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ ClassName EasyClassLoader
 * @ Description an easy class loader writed in the book
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:06
 * @ Version 1.0
 */
public class EasyClassLoader extends ClassLoader{
    private String name;
    private String path = "E:/";
    private final String fileType = ".class";

    public EasyClassLoader(String name){
        super();
        this.name = name;
    }
    public EasyClassLoader(ClassLoader parent,String name){
        super(parent);
        this.name = name;
    }
    public String toString(){ return this.name;}
    public void setPath(String path){ this.path=path;}
    public String getPath(){ return this.path;}

    private byte[] loadClassData(String name) throws ClassNotFoundException {
        FileInputStream fis = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
            name = name.replaceAll("\\.","/");
            fis = new FileInputStream(new File(path + name + fileType));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while((ch = fis.read())!=-1){
                baos.write(ch);
            }
            data = baos.toByteArray();
            fis.close();
            baos.close();
        }catch (IOException e){
            throw new ClassNotFoundException("Class Not Found : "+name,e);
        }
        return data;
    }
    protected Class findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return defineClass(name,data,0,data.length);
    }
    public static void test(ClassLoader loader)throws Exception{
        Class objClass = loader.loadClass("testAssert");
        Object obj = objClass.newInstance();
    }
    public static void main (String[] args) throws Exception {
        EasyClassLoader loader1 = new EasyClassLoader("loader1");
        loader1.setPath("E:/test/loader1/");
        EasyClassLoader loader2 = new EasyClassLoader("loader2");
        loader1.setPath("E:/test/loader2/");
        EasyClassLoader loader3 = new EasyClassLoader("loader3");
        loader1.setPath("E:/test/loader3/");
        test(loader2);
        test(loader3);
    }
}
