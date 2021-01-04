package com.ayy.jvm.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ ClassName DechiffreClassLoader
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/01/2021 20H
 * @ Version 1.0
 */
public class DechiffreClassLoader extends ClassLoader{
    private String root;

    public DechiffreClassLoader(String root) {
        super();
        this.root = root;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        if(null!=clazz){
            return clazz;
        }
        ClassLoader cl = this.getParent();
        try {
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
            is = new FileInputStream(path);
            int temp=0;
            while ((temp=is.read())!=-1){
                baos.write(temp^0xff);
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
