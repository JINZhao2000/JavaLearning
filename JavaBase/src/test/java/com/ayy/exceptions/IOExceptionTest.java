package com.ayy.exceptions;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @ ClassName IOExceptionTest
 * @ Description order of the catch of exception
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:00
 * @ Version 1.0
 */
public class IOExceptionTest {
    @Test
    public void test01(){
        FileReader reader = null;
        try {
            reader = new FileReader("e:/a.txt");
            char c1 = (char)reader.read();
            System.out.println(c1);
        }
        // subException should be before the parentException
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
