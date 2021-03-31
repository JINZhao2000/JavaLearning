package com.ayy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 31/03/2021
 * @ Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int uid;
    private String uname;
    private String pwd;

    public User(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }
}
