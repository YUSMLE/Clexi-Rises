package com.clexi.clexi.bluetoothle_central;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.clexi.clexi.app.Log;

/**
 * Created by Yousef on 2/12/2018.
 */

public class BLEService extends Service
{

    public static final String TAG = BLEService.class.getSimpleName();

    public static final String ACTION_START_BLE_SERVICE = "ACTION_START_BLE_SERVICE";

    /****************************************************
     * VARIABLES
     ***************************************************/

    /* Instance Approach */
    public static BLEService mInstance;

    /* Binder Aproach */
    private IBinder mBinder;

    /* Foreground Service Aproach */
    private NotificationManager mNotificationManager;

    /* BLE Manager */
    private BLEManager mBleManager;

    /****************************************************
     * SERVICE OVERRIDES
     ***************************************************/

    @Override
    public void onCreate()
    {
        super.onCreate();

        // DEBUG
        Log.d(TAG, "onCreate()");

        // Instance Approach
        BLEService.mInstance = this;

        // Binder Aproach
        mBinder = new LocalBinder();

        // Foreground Service Approach
        mNotificationManager = ForegroundServiceUtils.getNotificationManager(this);

        // BLE Manager
        mBleManager = BLEManager.getInstance();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        // DEBUG
        Log.d(TAG, "onStart()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // DEBUG
        Log.d(TAG, "onStartCommand()");

        try
        {
            if (intent.getAction().equals(ACTION_START_BLE_SERVICE))
            {
                ForegroundServiceUtils.startServiceAsForeground(this);
            }
            else
            {
                // TEMP: Start the BLE service as foreground service, anyway...
                ForegroundServiceUtils.startServiceAsForeground(this);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            // TEMP: Start the BLE service as foreground service, anyway...
            ForegroundServiceUtils.startServiceAsForeground(this);
        }

        // Return START_STICKY for start again service in background when application killed by OS
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // DEBUG
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        // DEBUG
        Log.d(TAG, "onConfigurationChanged()");
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();

        // DEBUG
        Log.d(TAG, "onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);

        // DEBUG
        Log.d(TAG, "onTrimMemory()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        // DEBUG
        Log.d(TAG, "onBind()");

        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        //stopForeground(true);

        return mBinder;
    }

    @Override
    public void onRebind(Intent intent)
    {
        // DEBUG
        Log.d(TAG, "onRebind()");

        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground service
        // when that happens.
        //stopForeground(true);

        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        // DEBUG
        Log.d(TAG, "onUnbind()");

        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.
        // In this particular example, close() is invoked
        // when the UI is disconnected from the Service.
        // TODO later...

        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        //startServiceAsForeground();

        // Ensures onRebind() is called when a client re-binds.
        //return true;

        return super.onUnbind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent)
    {
        try
        {
            // Clean up
            // TODO later...
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Clean up, anyway you can
            // TODO later...
        }

        super.onTaskRemoved(rootIntent);
    }

    /****************************************************
     * GETTERS & SETTERS
     ***************************************************/

    public BLEManager getBleManager()
    {
        return mBleManager;
    }

    /****************************************************
     * MANAGE SERVICE
     ***************************************************/

    public static void startService(Context context)
    {
        Intent intent = new Intent(context, BLEService.class);
        intent.setAction(ACTION_START_BLE_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            context.startForegroundService(intent);
        }
        else
        {
            context.startService(intent);
        }
    }

    public static void destroyService(Context context)
    {
        Intent intent = new Intent(context, BLEService.class);
        context.stopService(intent);
    }

    private void destroyService()
    {
        this.stopSelf();
    }

    /****************************************************
     * INNER CLASSES
     ***************************************************/

    public class LocalBinder extends Binder
    {
        /**
         * Class used for the client Binder.
         * Because we know this service always runs in the same process as its clients,
         * we don't need to deal with IPC.
         */
        public BLEService getService()
        {
            return BLEService.this;
        }
    }

}
