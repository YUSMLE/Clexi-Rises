package com.clexi.clexi.bluetoothle_central;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import com.clexi.clexi.app.App;
import com.clexi.clexi.app.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousef on 9/7/2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScanCallback extends android.bluetooth.le.ScanCallback
{

    public static final String TAG = ScanCallback.class.getSimpleName();

    /**
     * Constructor
     */
    public ScanCallback()
    {
        // Nothing
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result)
    {
        // DEBUG
        Log.d(TAG, "onScanResult, Address: " + result.getDevice().toString() + ", RSSI: " + result.getRssi());

        /* If we scan with report delay > 0, this will never be called. */

        // Check RSSI power for SEARCHING state
        // TODO later...

        // Check Server Name
        // TODO later...

        // Broadcast Advertise State
        Broadcast.notifyScanResult(App.getAppContext(), result);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results)
    {
        for (ScanResult sr : results)
        {
            // DEBUG
            Log.d(TAG, "onBatchScanResults: " + sr.toString());
        }

        // Broadcast Advertise State
        Broadcast.notifyBatchScanResults(App.getAppContext(), (ArrayList<ScanResult>) results);
    }

    @Override
    public void onScanFailed(int errorCode)
    {
        // DEBUG
        Log.d(TAG, "onScanFailed - errorCode: " + errorCode);

        /**
         * All SCAN_ERROR_CODES:
         *
         * 1 =>  SCAN_FAILED_ALREADY_STARTED:
         *       Fails to start scan as BLE scan with the same settings is already started by the app.
         *
         * 2 =>  SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
         *       Fails to start scan as app cannot be registered.
         *
         * 3 =>  SCAN_FAILED_INTERNAL_ERROR:
         *       Fails to start scan due an internal error
         *
         * 4 =>  SCAN_FAILED_FEATURE_UNSUPPORTED:
         *       Fails to start power optimized scan as this feature is not supported.
         *
         * 5 =>  SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES:
         *       Fails to start scan as it is out of hardware resources.
         *
         * 6 =>  SCAN_FAILED_SCANNING_TOO_FREQUENTLY:
         *       Fails to start scan as application tries to scan too frequently.
         */

        // Broadcast Advertise State
        Broadcast.notifyScanFailed(App.getAppContext(), errorCode);
    }
}
