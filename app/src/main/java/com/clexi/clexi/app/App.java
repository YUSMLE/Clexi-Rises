package com.clexi.clexi.app;

import android.app.Application;
import android.content.Context;

import com.clexi.clexi.BuildConfig;

/**
 * Created by Yousef on 2/27/2017.
 */

public class App extends Application
{

    public static final boolean DEBUG = BuildConfig.DEBUG;

    private static App mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mInstance = this;

        // Init Database
        // TODO later...

        // Set Context for BluetoothLE Framework
        // TODO later...

        // Start BluetoothLE Service
        // TODO later...
    }

    public static Context getAppContext()
    {
        return mInstance.getApplicationContext();
    }
}
