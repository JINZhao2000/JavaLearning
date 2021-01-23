package com.ayy.pojo;

import java.io.Serializable;

/**
 * @ ClassName Grade
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/01/2021 23H
 * @ Version 1.0
 */
public class Grade implements Serializable {
    private Integer gid;
    private String gname;

    public Grade() {}

    public Grade(String gname) {
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
        return "Grade{"+this.gname+"}";
    }
}
