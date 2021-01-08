package com.ayy.regex.spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ ClassName WebSpider
 * @ Description
 * @ Author Zhao JIN
 * @ Date 08/01/2021 22H
 * @ Version 1.0
 */
public class WebSpider {
    public static String getURLContent(String url, Charset c){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream(),c));
            String temp = "";
            while((temp=reader.readLine())!=null){
                sb.append(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<String> getMatherSubstrs(String destStr,String regexStr){
        List<String> resList = new ArrayList<>();
        Pattern p = Pattern.compile(regexStr);
        Matcher m = p.matcher(destStr);
        while (m.find()==true){
            resList.add(m.group(1));
        }
        return resList;
    }

    public static void main(String[] args) throws Exception{
        String content = WebSpider.getURLContent("https://www.163.com",Charset.forName("GBK"));
        String regexStr = "<a[\\s\\S]+?href=\"(http[\\w\\s./:]+?)\"[\\s\\S]+?</a>";
        List<String> resList = getMatherSubstrs(content,regexStr);
        resList.forEach(System.out::println);
    }
}
