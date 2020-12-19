package com.ayy.exceptions;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.*;

/**
 * @ ClassName LoggerTest
 * @ Description the use of logger, required logger in catch block of exception
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:46
 * @ Version 1.0
 */
public class LoggerTest {
    @Test
    public void test01() throws IOException {
        Logger logger = Logger.getLogger("myLogger");

        // order of the level of logger from high to low
        // when a level of logger is set the lower logger won't be showed
//		logger.setLevel(Level.SEVERE);
//		logger.setLevel(Level.WARNING);
//		logger.setLevel(Level.INFO);
//		logger.setLevel(Level.CONFIG);
//		logger.setLevel(Level.FINE);
//		logger.setLevel(Level.FINER);
//		logger.setLevel(Level.FINEST);
        // open all level of logger
//      logger.setLevel(Level.ALL);
        // close all level of logger
//      logger.setLevel(Level.OFF);

        FileHandler fileHandler = new FileHandler("./src/test/java/com/ayy/exceptions/test.log");
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format (LogRecord record) {
                return "<"+record.getLevel()+">:"+record.getMessage()+"\n";
            }
        });
        fileHandler.setLevel(Level.INFO);
        logger.addHandler(fileHandler);
        logger.info("level info");
        logger.warning("level warning");
        logger.severe("level severe");
    }
}
