package com.ayy.javaIO.encode;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @ ClassName EncodeTest
 * @ Description test foe encoding
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:20
 * @ Version 1.0
 */
public class EncodeTest {
    @Test
    public void test01() throws UnsupportedEncodingException {
        String msg ="vie";
        byte[] data = msg.getBytes();
        System.out.println(Arrays.toString(data));
        System.out.println(data.length);

        data = msg.getBytes("UTF-16LE");
        System.out.println(Arrays.toString(data));
        System.out.println(data.length);

        data = msg.getBytes("UTF-16BE");
        System.out.println(Arrays.toString(data));
        System.out.println(data.length);

        data = msg.getBytes("GBK");
        System.out.println(Arrays.toString(data));
        System.out.println(data.length);

        msg = new String(data,0,data.length,"UTF-8");
        System.out.println(msg);
    }
}
