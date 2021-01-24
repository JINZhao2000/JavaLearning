package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName Student
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/01/2021 23H
 * @ Version 1.0
 */
public class Student3 implements Serializable {
    private Integer sid;
    private String sname;
    private Integer age;
    private Grade3 grade;

    public Student3() {}

    public Student3(Integer sid, String sname, Integer age, Grade3 grade) {
        this.sid = sid;
        this.sname = sname;
        this.age = age;
        this.grade = grade;
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

    public Grade3 getGrade() {
        return grade;
    }

    public void setGrade(Grade3 grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{"+this.sname+","+this.age+","+this.grade+"}";
    }
}
