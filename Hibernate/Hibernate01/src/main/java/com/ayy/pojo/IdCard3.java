package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName IdCard3
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/01/2021 23H
 * @ Version 1.0
 */
public class IdCard3 implements Serializable {
    private Integer pid;
    private String code;
    private Person3 person;

    public Person3 getPerson() {
        return person;
    }

    public void setPerson(Person3 person) {
        this.person = person;
    }

    public IdCard3() {}

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
