package com.clexi.clexi.bluetoothle_central;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.ParcelUuid;

import com.clexi.clexi.app.App;
import com.clexi.clexi.bluetoothle_central.characteristics.CharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.CharacteristicHandlerFactory;
import com.clexi.clexi.bluetoothle_central.consts.Actions;
import com.clexi.clexi.bluetoothle_central.consts.Extras;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yousef on 5/19/2018.
 */

public class BLEClient
{

    public static final String TAG = BLEClient.class.getSimpleName();

    /****************************************************
     * VARIABLES
     ***************************************************/

    private Context mContext;

    /* Bluetooth LE Connection Requirements */
    private BluetoothManager mManager;
    private BluetoothAdapter mAdapter;

    private BluetoothGatt      mGatt;
    private GattCallback       mGattCallback;
    private LeScanCallback     mLeScanCallback;
    private ScanCallback       mScanCallback;
    private ScanSettings       mScanSettings;
    private List<ScanFilter>   mScanFilters;
    private BluetoothLeScanner mLeScanner;

    /* Gatt services and characteristics to be used */
    private Map<BluetoothGattCharacteristic, CharacteristicHandler> mHandlers;

    /* Listening For Bluetooth State Changes */
    private GattStateChangesReceiver mGattStateChangesReceiver;
    private IntentFilter             mGattStateFilter;

    /****************************************************
     * INITIALIZATION
     ***************************************************/

    public void setManager(BluetoothManager manager)
    {
        this.mManager = manager;
    }

    public void setAdapter(BluetoothAdapter adapter)
    {
        this.mAdapter = adapter;
    }

    /****************************************************
     * FUNCTIONALITY
     ***************************************************/

    public void setScanner()
    {
        if (mAdapter != null)
        {
            if (Build.VERSION.SDK_INT < 21)
            {
                mLeScanCallback = new LeScanCallback();
            }
            else
            {
                mScanCallback = new ScanCallback();

                mLeScanner = mAdapter.getBluetoothLeScanner();

                mScanSettings = new ScanSettings
                        .Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setReportDelay(100)
                        .build();

                // We only want to scan for devices advertising our custom service
                ScanFilter scanFilter = new ScanFilter
                        .Builder()
                        .setServiceUuid(new ParcelUuid(UUIDs.UUID_SERVICE_CLEXI))
                        .build();
                mScanFilters = Arrays.asList(scanFilter);
            }
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public void startScan()
    {
        if (mAdapter != null)
        {
            if (Build.VERSION.SDK_INT < 21)
            {
                mAdapter.startLeScan(mLeScanCallback);
            }
            else
            {
                mLeScanner.startScan(mScanFilters, mScanSettings, mScanCallback);
            }
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public void stopScan()
    {
        if (mAdapter != null)
        {
            if (Build.VERSION.SDK_INT < 21)
            {
                mAdapter.stopLeScan(mLeScanCallback);
            }
            else
            {
                mLeScanner.stopScan(mScanCallback);
            }
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public void connect(BluetoothDevice device)
    {
        if (mAdapter != null && device != null)
        {
            // Check if we was recently connected to this device,
            // to lite connect to that
            // TODO later...

            // We want to directly connect to the device, so we are setting the autoConnect parameter to false.
            if (Build.VERSION.SDK_INT < 23)
            {
                mGatt = device.connectGatt(mContext, false, mGattCallback);
            }
            else
            {
                mGatt = device.connectGatt(mContext, false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
                mGatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
            }

            // Refresh device cache
            // TODO later...
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public void disconnect()
    {
        if (mAdapter != null && mGatt != null)
        {
            try
            {
                mGatt.disconnect();
                mGatt.close();
                mGatt = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();

                // TODO: handle this state...
            }
        }
        else
        {
            // TODO: handle this state...
        }
    }

    /****************************************************
     * Getters & Setters
     ***************************************************/

    public CharacteristicHandler getHandler(BluetoothGattCharacteristic characteristic)
    {
        return mHandlers.get(characteristic);
    }

    /****************************************************
     * INNER CLASSES
     ***************************************************/

    class GattStateChangesReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (action.equals(Actions.ACTION_GATT_CONNECTION_STATE_CHANGED))
            {
                int newState = intent.getIntExtra(Extras.NEW_STATE, BluetoothProfile.STATE_DISCONNECTED);

                if (newState == BluetoothProfile.STATE_CONNECTED)
                {
                    // Discover services
                    mGatt.discoverServices();
                }
            }
            else if (action.equals(Actions.ACTION_GATT_SERVICES_DISCOVERED))
            {
                int status = intent.getIntExtra(Extras.STATUS, 0);

                // Retrieve services and characteristics
                if (status == BluetoothGatt.GATT_SUCCESS)
                {
                    BluetoothGattService clexiService = mGatt.getService(UUIDs.UUID_SERVICE_CLEXI);
                    if (clexiService != null)
                    {
                        List<BluetoothGattCharacteristic> clexiCharacteristics = clexiService.getCharacteristics();
                        if (clexiCharacteristics != null && clexiCharacteristics.size() != 0)
                        {
                            mHandlers = new HashMap<>();

                            for (BluetoothGattCharacteristic characteristic : clexiCharacteristics)
                            {
                                CharacteristicHandler handler = CharacteristicHandlerFactory.getCharacteristicHandler(mGatt, characteristic);
                                if (handler != null)
                                {
                                    mHandlers.put(characteristic, handler);
                                }
                                else
                                {
                                    // TODO: handle this state...
                                }
                            }
                        }
                        else
                        {
                            // TODO: handle this state...
                        }
                    }
                    else
                    {
                        // TODO: handle this state...
                    }
                }
                else
                {
                    // TODO: handle this state...
                }
            }
        }
    }

    /****************************************************
     * Lazy and Thread-safe Singleton Pattern
     ***************************************************/

    private BLEClient()
    {
        // Context
        mContext = App.getAppContext();

        /* Init receivers */

        mGattStateChangesReceiver = new GattStateChangesReceiver();
        mGattStateFilter = new IntentFilter();
        mGattStateFilter.addAction(Actions.ACTION_GATT_CONNECTION_STATE_CHANGED);
        mGattStateFilter.addAction(Actions.ACTION_GATT_SERVICES_DISCOVERED);
        mContext.registerReceiver(mGattStateChangesReceiver, mGattStateFilter);
    }

    private static class SingletonHolder
    {
        private static final BLEClient INSTANCE = new BLEClient();
    }

    public static BLEClient getInstance()
    {
        return SingletonHolder.INSTANCE;
    }
}
