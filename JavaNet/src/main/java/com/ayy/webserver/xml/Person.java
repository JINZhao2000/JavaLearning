package com.ayy.webserver.xml;

/**
 * @ ClassName Person
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/01/2021 21H
 * @ Version 1.0
 */
public class Person {
    private String name;
    private int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name:"+this.getName()+", age:"+age+"}";
    }
}
