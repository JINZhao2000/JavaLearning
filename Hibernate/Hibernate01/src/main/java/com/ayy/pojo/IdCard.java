package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName IdCard
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/01/2021 23H
 * @ Version 1.0
 */
public class IdCard implements Serializable {
    private Integer pid;
    private String code;

    public IdCard() {}

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
