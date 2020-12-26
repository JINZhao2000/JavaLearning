package com.ayy.ref.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @ ClassName DemoJS
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/12/2020 15H
 * @ Version 1.0
 */
public class DemoJS {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException, IOException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.put("msg","hello javascript");
        String str = "var user = {name:'abc',age:18};";
        str+="print(user.name)";
        engine.eval(str);
        System.out.println(engine.get("msg"));
        engine.eval("msg = 'hello world'");;
        System.out.println(engine.get("msg"));

        engine.eval("function add(a,b){return (a+b);}");
        Invocable jsInvoke = (Invocable)engine;
        Object res = jsInvoke.invokeFunction("add", 10,20);
        System.out.println(res);

        String jsCode = "var clazz = Java.type(\"java.util.Arrays\"); var list = clazz.asList([\"a\",\"b\",\"c\"]);";
        engine.eval(jsCode);
        List<String> list= (List<String>)engine.get("list");
        list.forEach(System.out::println);

        String path = DemoJS.class.getClassLoader().getResource("").getPath();
        FileReader reader = new FileReader(new File(path+"test.js"));
        engine.eval(reader);
        reader.close();
    }
}
