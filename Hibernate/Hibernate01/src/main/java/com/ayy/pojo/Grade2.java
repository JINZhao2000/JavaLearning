package com.ayy.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @ ClassName Grade2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/01/2021 23H
 * @ Version 1.0
 */
public class Grade2 implements Serializable {
    private Integer gid;
    private String gname;
    private Set<Student2> students;

    public Set<Student2> getStudents() {
        return students;
    }

    public void addStudent(Student2 stu){
        this.students.add(stu);
    }

    public void setStudents(Set<Student2> students) {
        this.students = students;
    }

    public Grade2() {
        students = new HashSet<>();
    }

    public Grade2(String gname) {
        this.gname = gname;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    @Override
    public String toString() {
        return "Grade{"+this.gname+","+this.students.toString()+"}";
    }
}
