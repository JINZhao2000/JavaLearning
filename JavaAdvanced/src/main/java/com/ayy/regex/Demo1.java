package com.ayy.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ ClassName Demo1
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/01/2021 23H
 * @ Version 1.0
 */
public class Demo1 {
    public static void main(String[] args) {
//        String str = "dasdhwqon123h12y38912h128ehw";
        String str = "dasdhwqon123h1&&2y38912h128ehw";
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(str);
//        System.out.println(m.matches());
        while(m.find()) {
            System.out.println(m.group());
            System.out.println(m.group(0));
        }
    }
}
