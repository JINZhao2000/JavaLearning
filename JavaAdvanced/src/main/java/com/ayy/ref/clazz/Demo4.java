package com.ayy.ref.clazz;

import com.ayy.ref.clazz.bean.User;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @ ClassName Demo4
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/12/2020 21H
 * @ Version 1.0
 */
public class Demo4 {
    public void test01 (Map<String, User> map, List<User> list){
        System.out.println("demo4 test01");
    }

    public Map<Integer,User> test02(){
        System.out.println("demo4 test02");
        return null;
    }

    public static void main(String[] args) throws Exception{
        Method m = Demo4.class.getMethod("test01", Map.class, List.class);
        Type[] ts = m.getGenericParameterTypes();
        for (Type t: ts){
            System.out.println("Parameter Type : "+t);
            if(t instanceof ParameterizedType){
                Type[] gts = ((ParameterizedType) t).getActualTypeArguments();
                for (Type gt : gts){
                    System.out.println("Generic Type : "+gt);
                }
            }
        }

        Method m2 = Demo4.class.getMethod("test02",null);
        Type rt = m2.getGenericReturnType();
        System.out.println("Return Type : "+rt);
        if(rt instanceof ParameterizedType){
            Type[] gts = ((ParameterizedType) rt).getActualTypeArguments();
            for (Type gt : gts){
                System.out.println("Return generic type : "+gt);
            }
        }
    }
}
