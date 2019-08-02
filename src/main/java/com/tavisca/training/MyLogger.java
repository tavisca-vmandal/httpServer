package com.tavisca.training;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {
    static Logger logger=LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);


    public void log(String message)
    {
        try {
            FileHandler fileHandler=new FileHandler("logFile.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO,message);
    }
}
