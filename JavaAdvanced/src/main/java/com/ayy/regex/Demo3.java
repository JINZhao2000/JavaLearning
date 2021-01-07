package com.ayy.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ ClassName Demo3
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/01/2021 23H
 * @ Version 1.0
 */
public class Demo3 {
    public static void main(String[] args) {
        String str = "dasdhwq242&&on123&&h1&&2y38912h128ehw";
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(str);
        System.out.println(m.replaceAll("#"));
    }
}
