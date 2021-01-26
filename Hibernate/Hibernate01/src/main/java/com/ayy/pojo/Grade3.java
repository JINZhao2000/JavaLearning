package com.ayy.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @ ClassName Grade3
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/01/2021 23H
 * @ Version 1.0
 */
public class Grade3 implements Serializable {
    private Integer gid;
    private String gname;
    private Set<Student3> students;

    public Set<Student3> getStudents() {
        return students;
    }

    public void addStudent(Student3 stu){
        this.students.add(stu);
    }

    public void setStudents(Set<Student3> students) {
        this.students = students;
    }

    public Grade3() {
        students = new HashSet<>();
    }

    public Grade3(String gname) {
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
