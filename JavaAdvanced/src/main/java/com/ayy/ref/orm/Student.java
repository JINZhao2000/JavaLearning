package com.ayy.ref.orm;

/**
 * @ ClassName Student
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/12/2020 11H
 * @ Version 1.0
 */

@Table("tb_student")
public class Student {
    @Field(columuName = "uid",type = "int",length = 10)
    private int sid;
    @Field(columuName = "nameStudent",type = "varchar",length = 10)
    private String nameStudent;
    @Field(columuName = "age",type = "int",length = 3)
    private int age;



    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
