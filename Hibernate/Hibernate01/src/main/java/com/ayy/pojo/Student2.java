package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName Student
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/01/2021 23H
 * @ Version 1.0
 */
public class Student2 implements Serializable {
    private Integer sid;
    private String sname;
    private Integer age;

    public Student2() {}

    public Student2(Integer sid, String sname, Integer age) {
        this.sid = sid;
        this.sname = sname;
        this.age = age;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{"+this.sname+","+this.age+"}";
    }
}
