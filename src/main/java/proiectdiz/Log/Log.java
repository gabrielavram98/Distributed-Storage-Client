package proiectdiz.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger logger = LoggerFactory.getLogger(Log.class);
    public static void TraceLog(String message){
        logger.trace(message);
    }

    public static void DebugLog(String message){
        logger.debug(message);
    }
    public static void InfoLog(String message){
        logger.info(message);
    }
    public static void WarnLog(String message){
        logger.warn(message);
    }
    public static void ErrorLog(String message){
        logger.error(message);
    }


}
