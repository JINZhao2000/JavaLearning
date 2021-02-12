package com.ayy.entities;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
public class Emp {
    private Integer eid;
    private String ename;
    private Double salary;
    private Integer age;

    public Emp() {}

    public Emp(Integer eid, String ename, Double salary, Integer age) {
        this.eid = eid;
        this.ename = ename;
        this.salary = salary;
        this.age = age;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "eid=" + eid +
                ", ename='" + ename + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}
