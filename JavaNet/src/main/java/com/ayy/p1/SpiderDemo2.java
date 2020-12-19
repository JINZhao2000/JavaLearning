package com.ayy.p1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ ClassName SpiderDemo2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 22H
 * @ Version 1.0
 */
public class SpiderDemo2 {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://edt.iut-tlse3.fr/planning/info/g8704.html#");
        // when 403 forbidden
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        String msg = null;
        while (null!=(msg=br.readLine())){
            System.out.println(msg);
        }
        br.close();
    }
}
