package com.clexi.clexi.app;

/**
 * Created by Yousef on 8/11/2018.
 */

public class Log
{

    public static final String TAG = Log.class.getSimpleName();

    /* Log Level */
    private enum Level
    {
        Info,
        Debug,
        Warn,
        Error,
        Assert
    }

    /****************************************************
     * Logs
     ***************************************************/

    // Debug
    public void debug(String tag, String msg)
    {
        android.util.Log.d(tag, msg);

        saveLogInFile(Level.Debug, tag, msg);
    }

    // Info
    public void info(String tag, String msg)
    {
        android.util.Log.d(tag, msg);

        saveLogInFile(Level.Info, tag, msg);
    }

    // Warn
    public void warn(String tag, String msg)
    {
        android.util.Log.d(tag, msg);

        saveLogInFile(Level.Warn, tag, msg);
    }

    // Error
    public void error(String tag, String msg)
    {
        android.util.Log.e(tag, msg);

        saveLogInFile(Level.Error, tag, msg);
    }

    /****************************************************
     * Errors
     ***************************************************/

    // Error
    public void error(String tag, String msg, Exception e)
    {
        android.util.Log.e(tag, msg);

        e.printStackTrace();

        saveLogInFile(Level.Error, tag, msg, e);
    }

    /****************************************************
     * Log File
     ***************************************************/

    private void saveLogInFile(Level level, String tag, String msg)
    {
        // TODO later...
    }

    private void saveLogInFile(Level level, String tag, String msg, Exception e)
    {
        // TODO later...
    }

    /****************************************************
     * Lazy and Thread-safe Singleton Pattern
     ***************************************************/

    private Log()
    {
        // Nothing
    }

    private static class SingletonHolder
    {
        private static final Log INSTANCE = new Log();
    }

    public static Log getInstance()
    {
        return Log.SingletonHolder.INSTANCE;
    }

    /****************************************************
     * ACCESSORY
     ***************************************************/

    public static void d(String tag, String msg)
    {
        getInstance().debug(tag, msg);
    }

    public static void i(String tag, String msg)
    {
        getInstance().info(tag, msg);
    }

    public static void w(String tag, String msg)
    {
        getInstance().warn(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        getInstance().error(tag, msg);
    }

    public static void e(String tag, String msg, Exception e)
    {
        getInstance().error(tag, msg);
    }
}
