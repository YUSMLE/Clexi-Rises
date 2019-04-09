package com.clexi.clexi.bluetoothle_central;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.clexi.clexi.app.App;
import com.clexi.clexi.app.Log;

/**
 * Created by yousef on 9/7/2016.
 */

public class LeScanCallback implements BluetoothAdapter.LeScanCallback
{
    public static final String TAG = LeScanCallback.class.getSimpleName();

    /**
     * Constructor
     */
    public LeScanCallback()
    {
        // Nothing
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord)
    {
        // DEBUG
        Log.d(TAG, "onLeScan, Address: " + device.toString() + ", RSSI: " + rssi);

        // Check RSSI power for SEARCHING state
        // TODO later...

        // Check Server Name
        // TODO later...

        // Broadcast Advertise State
        Broadcast.notifyScanResult(App.getAppContext(), device, rssi, scanRecord);
    }
}
