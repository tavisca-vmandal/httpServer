package com.tavisca.training;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {

    public static Logger logger=LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static FileHandler fileHandler;
    public static void log(String message)
    {
        try {
            fileHandler=new FileHandler("log/logFile.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO,message);
    }
}
