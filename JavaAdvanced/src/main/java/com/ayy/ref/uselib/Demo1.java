package com.ayy.ref.uselib;

import javassist.*;

import java.io.IOException;

/**
 * @ ClassName Demo1
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/12/2020 21H
 * @ Version 1.0
 */
public class Demo1 {
    public static void main(String[] args) throws CannotCompileException, NotFoundException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("com.ayy.ref.uselib.Emp");

        CtField f1 = CtField.make("private int empno;",cc);
        CtField f2 = CtField.make("private String ename;",cc);
        cc.addField(f1);
        cc.addField(f2);

        CtMethod m1 = CtMethod.make("public int getEmpno(){return empno;}",cc);
        CtMethod m2 = CtMethod.make("public void setEmpno(int empno){this.empno = empno;}",cc);
        cc.addMethod(m1);
        cc.addMethod(m2);

        CtConstructor c1 = new CtConstructor(new CtClass[]{CtClass.intType,pool.get("java.lang.String")},cc);
        c1.setBody("{this.empno=empno; this.ename=ename;}");
        cc.addConstructor(c1);
        cc.writeFile("d:/JavassistTest");
    }
}
