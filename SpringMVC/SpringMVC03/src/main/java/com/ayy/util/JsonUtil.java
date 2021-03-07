package com.ayy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/03/2021
 * @ Version 1.0
 */
public class JsonUtil {
    public static String getJson(Object object) throws JsonProcessingException {
        return getJson(object,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getJson(Object object, SimpleDateFormat format) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.setDateFormat(format);
        return mapper.writeValueAsString(object);
    }
}
