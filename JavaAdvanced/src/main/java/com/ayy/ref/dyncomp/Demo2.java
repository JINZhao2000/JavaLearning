package com.ayy.ref.dyncomp;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ ClassName Demo2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/12/2020 22H
 * @ Version 1.0
 */
public class Demo2 {
    public static void main(String[] args) throws IOException {
        String str = "public class ABC{public static void main(String[] args){System.out.println(\"Hello compiler\")}}";
        File temp = File.createTempFile("ABC",".java");
        temp.deleteOnExit();
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        bw.write(str);
        System.out.println(temp.getAbsoluteFile());

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int res = compiler.run(null,null,null,temp.getAbsolutePath());
        System.out.println(res);
        bw.close();
    }
}
