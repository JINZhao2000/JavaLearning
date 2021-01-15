package com.ayy.MySQL.dao;

/**
 * @ ClassName CastUtils
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 23H
 * @ Version 1.0
 */
public class CastUtils {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    private CastUtils() {
        throw new UnsupportedOperationException();
    }
}
