package com.ayy.regex;

import java.util.Arrays;

/**
 * @ ClassName Demo4
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/01/2021 23H
 * @ Version 1.0
 */
public class Demo4 {
    public static void main(String[] args) {
        String str = "dasdhwq242on123h12y38912h128ehw";
        String[] arr = str.split("\\d+");
        System.out.println(Arrays.toString(arr));
    }
}
