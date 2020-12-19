package com.ayy.javaIO.printStream;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName PrintIOTest
 * @ Description test the uses of PrintStream
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:56
 * @ Version 1.0
 */
public class PrintStreamTest {
    @Test
    public void test01(){
        try {
            PrintWriter pw = new PrintWriter(new BufferedOutputStream(
                    new FileOutputStream(System.getProperty("user.dir")+ "/src/main/resources/PrintStream.txt")),true);
            pw.println("PrintStream");
            pw.println(true);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * redirect the output Console <-> File
     */
    public void test02(){
        PrintStream ps = System.out;
        ps.println("PrintStream");
        ps.println(true);

        try {
            // true -> autoFlush
            ps = new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(System.getProperty("user.dir")+ "/src/main/resources/PrintStream.txt")),true);
            ps.println("PrintStream");
            ps.println(true);
            // ps.flush();
            ps.close();

            // redirect output
            System.setOut(ps);
            System.out.println("change");
            // redirect output - back to console
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(FileDescriptor.out)),true));
            System.out.println("change");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
