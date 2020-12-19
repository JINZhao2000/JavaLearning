package com.ayy.javaIO.objectIOStream;

import java.io.Serializable;

/**
 * @ ClassName Employee
 * @ Description JavaBean Employee
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:31
 * @ Version 1.0
 */

public class Employee implements Serializable {
    private transient String name;
    //transient means it won't be serialized
    private double salary;

    public Employee(){}
    public Employee (String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public double getSalary () {
        return salary;
    }

    public void setSalary (double salary) {
        this.salary = salary;
    }

    @Override
    public String toString () {
        return "employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
