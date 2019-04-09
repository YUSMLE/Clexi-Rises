package com.clexi.clexi.helper;

/**
 * Created by yousef on 8/2/2016.
 */

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.clexi.clexi.app.Log;

/**
 * Getting Unique Device ID of an Android Smartphone
 * 
 * In Android there are many alternatives to UDID of the device. Some of the methods to get the UDID
 * in android application are listed below with its advantages and disadvantages and
 * any necessary permissions for getting the device ID.
 * 
 * 1. The IMEI: (International Mobile Equipment Identity)
 * 2. The Android ID
 * 3. The WLAN MAC Address string
 * 4. The Bluetooth Address string
 */
public class AndroidUDID
{

    /**
     * The IMEI: (International Mobile Equipment Identity)
     * 
     * require the permission “android.permission.READ_PHONE_STATE”
     * 
     * Advantages of using IMEI as Device ID:
     * - The IMEI is unique for each and every device.
     * - It remains unique for the device even if the application is re-installed or if the device is rooted or factory reset.
     * 
     * Disadvantages of using IMEI as Device ID:
     * - IMEI is dependent on the Simcard slot of the device,
     * so it is not possible to get the IMEI for the devices that do not use Simcard.
     * - In Dual sim devices, we get 2 different IMEIs for the same device as it has 2 slots for simcard.
     *
     * @param context
     * @return IMEI String
     */
    public static String getIMEI(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String           deviceId         = telephonyManager.getDeviceId();

        // DEBUG
        Log.d("AndroidUDID", "The IMEI: " + deviceId);

        return deviceId;
    }

    /**
     * The Android ID
     * 
     * Advantages of using Android_ID as Device ID:
     * - It is unique identifier for all type of devices (smart phones and tablets).
     * - No need of any permission.
     * - It will remain unique in all the devices and it works on phones without Simcard slot.
     * 
     * Disadvantages of using Android_ID as Device ID:
     * - The ID gets changed on Rooted phones and when the phone is factory reset.
     * - Also there is a known problem with a Chinese manufacturer of android device that some devices have same Android_ID.
     * - The ID of the device is sometimes different for different users for Android 4.2 devices as described here.
     *
     * @param context
     * @return Android ID String
     */
    public static String getAndroidId(Context context)
    {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        // DEBUG
        Log.d("AndroidUDID", "The Android ID: " + deviceId);

        return deviceId;
    }

    /**
     * The WLAN MAC Address string
     * 
     * Your application will require the permission “android.permission.ACCESS_WIFI_STATE”
     * 
     * Advantages of using WLAN MAC address as Device ID:
     * - It is unique identifier for all type of devices (smart phones and tablets).
     * - It remains unique if the application is reinstalled
     * 
     * Disadvantages of using WLAN MAC address as Device ID:
     * - If device doesn’t have wifi hardware then you get null MAC address,
     * but generally it is seen that most of the Android devices have wifi hardware
     * and there are hardly few devices in the market with no wifi hardware.
     *
     * @param context
     * @return MAC Address String
     */
    public static String getMacAddress(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String      deviceId    = wifiManager.getConnectionInfo().getMacAddress();

        // DEBUG
        Log.d("AndroidUDID", "The WLAN MAC Address string: " + deviceId);

        return deviceId;
    }

    /**
     * The Bluetooth Address string
     * 
     * Your application needs the permission “android.permission.BLUETOOTH”
     * 
     * Advantages of using Bluetooth device address as Device ID:
     * - It is unique identifier for all type of devices (smart phones and tablets).
     * - There is generally a single Bluetooth hardware in all devices and it doesn’t gets changed.
     * 
     * Disadvantages of using Bluetooth device address as Device ID:
     * - If device hasn’t bluetooth hardware then you get null.
     *
     * @param context
     * @return Bluetooth Address String
     */
    public static String getBluetoothAddress(Context context)
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String           deviceId         = bluetoothAdapter.getAddress();

        // DEBUG
        Log.d("AndroidUDID", "The Bluetooth Address string: " + deviceId);

        return deviceId;
    }

    /**
     * TUTORIAL
     *
     *  - using {@link package.class#member label}
     *      ALSO CHECK THE {@link com.vancosys.ndksample.AndroidUDID#getAndroidId(Context) getAndroidId()}
     *
     *  - using @see <a href="address">label</a>
     *      @see <a href="http://google.com">here</a>
     *
     *  - using <a href="address">label</a>
     *      See <a href="http://google.com">here</a>
     */
}
