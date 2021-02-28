package com.ayy.log;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 28/02/2021
 * @ Version 1.0
 */
public class Log4JTest {
    static Logger logger = Logger.getLogger(Log4JTest.class);

    @Test
    public void testLog4J(){
        logger.debug("DEBUG: Test Log4j");
        logger.info("INFO: Test Log4j");
        logger.error("ERROR: Test Log4j");
    }
}
