package com.tavisca.training;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {

    public static Logger logger=LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static FileHandler fileHandler;

    static
    {
        try {
            fileHandler=new FileHandler("log/logFile.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
