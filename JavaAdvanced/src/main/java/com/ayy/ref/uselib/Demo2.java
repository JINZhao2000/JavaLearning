package com.ayy.ref.uselib;

import javassist.*;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ ClassName Demo2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/12/2020 21H
 * @ Version 1.0
 */
public class Demo2 {
    public static void main(String[] args) throws Exception {
        test06();
    }

    public static void test01() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.ayy.ref.uselib.Emp");
        byte[] bytes = cc.toBytecode();
        System.out.println(Arrays.toString(bytes));
        System.out.println(cc.getName());
        System.out.println(cc.getSimpleName());
        System.out.println(cc.getSuperclass());
        System.out.println(cc.getInterfaces());
    }

    // add a method
    public static void test02() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.ayy.ref.uselib.Emp");

//        CtMethod m1 = CtNewMethod.make("public int add(int a, int b){return a+b;}",cc);
        CtMethod m1 = new CtMethod(CtClass.intType,"add",new CtClass[]{CtClass.intType,CtClass.intType},cc);
        m1.setModifiers(Modifier.PUBLIC);
        // $0 = this $1 = first param $2 = second param
        m1.setBody("{System.out.println(\"hello javassist\");return $1+$2;}");

        cc.addMethod(m1);

        Class c = cc.toClass();
        Object obj = c.getDeclaredConstructor().newInstance();
        Method m = c.getDeclaredMethod("add",int.class,int.class);
        Object res = m.invoke(obj,100,200);
        System.out.println(res);
    }

    // modify a method
    public static void test03() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.ayy.ref.uselib.Emp");

        CtMethod cm = cc.getDeclaredMethod("test",new CtClass[]{CtClass.intType});
        cm.insertBefore("System.out.println($1);System.out.println(\"---start---\");");

        cm.insertAfter("System.out.println(\"---end---\");");

        cm.insertAt(17,"System.out.println(\"before line 17\");");

        Class c = cc.toClass();
        Object obj = c.getDeclaredConstructor().newInstance();
        Method m = c.getDeclaredMethod("test",int.class);
        Object res = m.invoke(obj,100);
        System.out.println(res);
    }

    // add a field
    public static void test04() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.ayy.ref.uselib.Emp");

//        CtField f1 = CtField.make("private int empno;",cc);
        CtField f1 = new CtField(CtClass.intType,"salary",cc);
        f1.setModifiers(Modifier.PRIVATE);
        cc.addField(f1,"1000");

        cc.addMethod(CtNewMethod.getter("getSalary",f1));
        cc.addMethod(CtNewMethod.setter("setSalary",f1));

        Class c = cc.toClass();
        Object obj = c.getDeclaredConstructor().newInstance();
        Method m = c.getDeclaredMethod("getSalary",null);
        Object res = m.invoke(obj,null);
        System.out.println(res);
    }

    // constructor
    public static void test05() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.ayy.ref.uselib.Emp");

        CtConstructor[] ccon  = cc.getConstructors();
        for(CtConstructor c : ccon){
            System.out.println(c.getLongName());
        }
    }

    public static void test06() throws Exception {
        CtClass cc = ClassPool.getDefault().get("com.ayy.ref.uselib.Emp");
        Object[] all = cc.getAnnotations();
        Author a = (Author)all[0];
        String name = a.name();
        int year = a.year();
        System.out.println("name : "+a.name()+", year : "+a.year());
    }
}
