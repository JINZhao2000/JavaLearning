package com.ayy.anno;

import java.util.Date;

/**
 * @ ClassName AnnoTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/12/2020 18H
 * @ Version 1.0
 */
public class AnnoTest1 {
    @Override
    @Deprecated
    @SuppressWarnings("all")
    public String toString() {
        Date d = new Date();
        return super.toString();
    }
}
