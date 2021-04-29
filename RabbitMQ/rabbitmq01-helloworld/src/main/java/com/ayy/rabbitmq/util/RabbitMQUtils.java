package com.ayy.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 27/04/2021
 * @ Version 1.0
 */

public class RabbitMQUtils {
    private static final ThreadLocal<Connection> thConnection = new ThreadLocal<>();

    public static Connection getConnection() {
        Connection connection = RabbitMQUtils.thConnection.get();
        if(connection == null){
            InputStream is = null;
            Properties properties = new Properties();
            try {
                is = RabbitMQUtils.class.getClassLoader().getResourceAsStream("rabbitmq.properties");
                properties.load(is);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(is);
            }
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.load(properties);
            try {
                connection = connectionFactory.newConnection();
                thConnection.set(connection);
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void close(AutoCloseable... clos){
        for (AutoCloseable clo : clos) {
            if(clo!=null){
                try {
                    clo.close();
                    if(clo instanceof Connection){
                        thConnection.remove();
                    }
                } catch (Exception e) {
                    // nothing
                }
            }
        }
    }
}
