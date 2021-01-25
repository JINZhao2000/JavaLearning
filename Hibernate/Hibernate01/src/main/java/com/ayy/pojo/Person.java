package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName Person
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/01/2021 23H
 * @ Version 1.0
 */
public class Person implements Serializable {
    private Integer pid;
    private String pname;
    private Integer age;
    private IdCard card;

    public Person() {}

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public IdCard getCard() {
        return card;
    }

    public void setCard(IdCard card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Person{"+this.pname+","+this.age+","+this.card+"}";
    }
}
