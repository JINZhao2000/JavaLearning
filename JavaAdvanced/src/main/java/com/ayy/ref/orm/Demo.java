package com.ayy.ref.orm;

import java.lang.reflect.Field;

/**
 * @ ClassName Demo
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/12/2020 11H
 * @ Version 1.0
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        Class classStudent = Class.forName("com.ayy.ref.orm.Student");
//        Annotation[] annotations = classStudent.getDeclaredAnnotations();
        Table table = (Table) classStudent.getAnnotation(Table.class);
        System.out.println(table.value());
        Field f = classStudent.getDeclaredField("nameStudent");
        com.ayy.ref.orm.Field field = f.getAnnotation(com.ayy.ref.orm.Field.class);
        System.out.println(field.columuName()+"--"+field.type()+"--"+field.length());
    }
}
