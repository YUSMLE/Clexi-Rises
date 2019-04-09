package com.clexi.clexi.helper;

import android.os.Build;

/**
 * Created by Yousef on 4/29/2018.
 */

public class DeviceInformation
{

    public static String getDeviceInformation()
    {

        String defaultInfromation = "Information Unavailable";
        String deviceInfromation;

        try
        {
            deviceInfromation = getDeviceName() + " - Android " + getDeviceOs();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            // Default Information
            deviceInfromation = defaultInfromation;
        }

        return (deviceInfromation != null) ? deviceInfromation : defaultInfromation;
    }

    public static String getDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model        = Build.MODEL;

        if (model.toLowerCase().startsWith(manufacturer.toLowerCase()))
        {
            return capitalize(model);
        }
        else
        {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String getDeviceOs()
    {
        String os = Build.VERSION.RELEASE;

        return os;
    }

    public static String capitalize(String s)
    {
        if (s == null || s.length() == 0)
        {
            return "";
        }

        char first = s.charAt(0);

        if (Character.isUpperCase(first))
        {
            return s;
        }
        else
        {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
