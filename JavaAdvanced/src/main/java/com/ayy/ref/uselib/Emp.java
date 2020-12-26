package com.ayy.ref.uselib;

/**
 * @ ClassName Emp
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/12/2020 21H
 * @ Version 1.0
 */
@Author(name="a",year=1)
public class Emp {
    private int empno;
    private String ename;

    public Emp() {}

    public void test(int a){
        System.out.println("test method "+ a);
    }

    public Emp(int empno, String ename) {
        this.empno = empno;
        this.ename = ename;
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
