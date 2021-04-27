package com.ayy.rabbitmq.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 27/04/2021
 * @ Version 1.0
 */

public class RabbitMQUtils {
    private static final Properties properties = new Properties();

    static {
        InputStream is = null;
        try {
            is = RabbitMQUtils.class.getClassLoader().getResourceAsStream("rabbitmq.properties");
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void close(AutoCloseable... clos){
        for (AutoCloseable clo : clos) {
            if(clo!=null){
                try {
                    clo.close();
                } catch (Exception e) {
                    // nothing
                }
            }
        }
    }
}
