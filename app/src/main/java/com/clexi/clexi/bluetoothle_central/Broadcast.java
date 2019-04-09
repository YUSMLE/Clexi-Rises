package com.clexi.clexi.bluetoothle_central;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import com.clexi.clexi.app.Log;

import com.clexi.clexi.bluetoothle_central.consts.Actions;
import com.clexi.clexi.bluetoothle_central.consts.Extras;

import java.util.ArrayList;

/**
 * Created by Yousef on 4/3/2018.
 */

public class Broadcast
{

    public static final String TAG = Broadcast.class.getSimpleName();

    public static void notifyBLEStateChange(Context context)
    {
        String action = Actions.ACTION_BLE_STATE_CHANGE;

        try
        {
            final Intent intent = new Intent(action);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyConnectionStateChange(Context context, int status, int newState)
    {
        String action = Actions.ACTION_GATT_CONNECTION_STATE_CHANGED;

        try
        {
            final Intent intent = new Intent(action);
            intent.putExtra(Extras.STATUS, newState);
            intent.putExtra(Extras.NEW_STATE, newState);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyServicesDiscovered(Context context, int status)
    {
        String action = Actions.ACTION_GATT_SERVICES_DISCOVERED;

        try
        {
            final Intent intent = new Intent(action);
            intent.putExtra(Extras.STATUS, status);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyReadRemoteRssi(Context context, int rssi, int status)
    {
        String action = Actions.ACTION_GATT_READ_REMOTE_RSSI;

        try
        {
            final Intent intent = new Intent(action);
            intent.putExtra(Extras.RSSI, rssi);
            intent.putExtra(Extras.STATUS, status);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyScanResult(Context context, BluetoothDevice device, int rssi, byte[] scanRecord)
    {
        String action = Actions.ACTION_SCAN_RESULT;

        try
        {
            final Intent intent = new Intent(action);
            intent.putExtra(Extras.BLUETOOTH_DEVICE, device);
            intent.putExtra(Extras.RSSI, rssi);
            intent.putExtra(Extras.SCAN_RECORD, scanRecord);

            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyScanResult(Context context, ScanResult scanResult)
    {
        String action = Actions.ACTION_SCAN_RESULT;

        try
        {
            final Intent intent = new Intent(action);
            intent.putExtra(Extras.SCAN_RESULT, scanResult);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyBatchScanResults(Context context, ArrayList<ScanResult> scanResults)
    {
        String action = Actions.ACTION_BATCH_SCAN_RESULTS;

        try
        {
            final Intent intent = new Intent(action);
            intent.putParcelableArrayListExtra(Extras.BATCH_SCAN_RESULTS, scanResults);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }

    public static void notifyScanFailed(Context context, int errorCode)
    {
        String action = Actions.ACTION_SCAN_FAILED;

        try
        {
            final Intent intent = new Intent(action);
            intent.putExtra(Extras.ERROR_CODE, errorCode);
            context.sendBroadcast(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error with Broadcast " + action, e);
        }
    }
}
