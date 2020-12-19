package com.ayy.p1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @ ClassName SpiderDemo
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 22H
 * @ Version 1.0
 */
public class SpiderDemo {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://edt.iut-tlse3.fr/planning/info/g8704.html#");
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String msg = null;
        while (null!=(msg=br.readLine())){
            System.out.println(msg);
        }
        br.close();
    }
}
