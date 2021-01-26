package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName IdCard2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/01/2021 23H
 * @ Version 1.0
 */
public class IdCard2 implements Serializable {
    private Integer pid;
    private String code;
    private Person2 person;

    public Person2 getPerson() {
        return person;
    }

    public void setPerson(Person2 person) {
        this.person = person;
    }

    public IdCard2() {}

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "IdCard{"+this.code+"}";
    }
}
