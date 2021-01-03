package com.ayy.jvm.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @ ClassName FileSystemClassLoader
 * @ Description
 * @ Author Zhao JIN
 * @ Date 03/01/2021 16H
 * @ Version 1.0
 */
public class URLClassLoader extends ClassLoader{
    private String root;

    public URLClassLoader(String root) {
        super();
        this.root = root;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        if(null!=clazz){
            return clazz;
        }
        try {
            ClassLoader cl = this.getParent();
            clazz = cl.loadClass(name);
        }catch (ClassNotFoundException e){
            System.out.println("Initialize by user defined class loader");
        }
        if(null!=clazz){
            return clazz;
        }
        byte[] classData = getClassData(name);
        if(null==classData){
            throw new ClassNotFoundException();
        }else{
            clazz = defineClass(name,classData,0,classData.length);
        }
        return clazz;
    }

    private byte[] getClassData(String name){
        String path = root+"/"+name.replace(".","/")+".class";
        byte[] buffer = new byte[1024];
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            URL url = new URL(path);
            is = url.openStream();
            is = new FileInputStream(path);
            int temp=0;
            while ((temp=is.read(buffer))!=-1){
                baos.write(buffer,0,temp);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
