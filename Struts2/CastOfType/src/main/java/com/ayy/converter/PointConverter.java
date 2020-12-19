package com.ayy.converter;

import com.ayy.vo.Point;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

/**
 * @ ClassName PointConverter
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 20
 * @ Version 1.0
 */
public class PointConverter extends StrutsTypeConverter {
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String value = values[0];
        Point point = new Point();
        int index = value.indexOf(",");
        point.setX(Integer.parseInt(value.substring(1,index)));
        point.setY(Integer.parseInt(value.substring(index+1,value.length()-1)));
        return point;
    }

    // 使用 ognl 表达式会调用该方法
    @Override
    public String convertToString(Map context, Object o) {
        return o.toString();
    }
}
