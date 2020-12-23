package com.ayy.ref.dyncomp;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

/**
 * @ ClassName Demo3
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/12/2020 22H
 * @ Version 1.0
 */
public class Demo3 {
    public static void main(String[] args) throws Exception {
        String str = "public class ABC{public static void main(String[] args){System.out.println(\"Hello compiler\");}}";
        File temp = new File("d:/ABC.java");
        temp.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        bw.write(str);
        bw.flush();
        String path = temp.getParentFile().toString();
        path = path.replace("\\","/");
        String compilerpath = path+temp.getName();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int res = compiler.run(null,null,null,compilerpath);
        System.out.println(res);

        String name = temp.getName().substring(0,temp.getName().indexOf("."));

        Runtime run = Runtime.getRuntime();
        Process process = run.exec("java -cp "+path+" "+name);

        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = "";
        while((info=br.readLine())!=null){
            System.out.println(info);
        }
        bw.close();
    }
}
