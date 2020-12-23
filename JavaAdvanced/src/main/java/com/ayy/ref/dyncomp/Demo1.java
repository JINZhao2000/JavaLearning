package com.ayy.ref.dyncomp;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;

/**
 * @ ClassName Demo1
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/12/2020 22H
 * @ Version 1.0
 */
public class Demo1 {
    public static void main(String[] args) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int res = compiler.run(null,null,null,"d:/ABC.java");
        System.out.println(res);
    }
}
