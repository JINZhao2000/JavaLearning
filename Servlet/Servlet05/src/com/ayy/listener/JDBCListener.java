package com.ayy.listener;

import com.ayy.utils.DBUtils;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/02/2021
 * @ Version 1.0
 */
@WebListener
public class JDBCListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new Thread(()->DBUtils.getConnection()).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            while(DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
