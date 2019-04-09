package com.clexi.clexi.bluetoothle_central;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;

import com.clexi.clexi.app.App;
import com.clexi.clexi.app.Log;
import com.clexi.clexi.bluetoothle_central.consts.Actions;
import com.clexi.clexi.bluetoothle_central.consts.Extras;

import java.util.List;

/**
 * Created by Yousef on 2/12/2018.
 */

public class BLEManager
{

    public static final String TAG = BLEManager.class.getSimpleName();

    /****************************************************
     * VARIABLES
     ***************************************************/

    private Context mContext;

    /* Bluetooth LE Connection Requirements */
    private BluetoothManager mManager;
    private BluetoothAdapter mAdapter;

    private BLEClient mClient;

    private BluetoothDevice mDevice;

    /* Bluetooth LE Connection States */
    private BLEState mState;

    /* Listening For Bluetooth State Changes */
    private BluetoothStateChangesReceiver mBluetoothStateChangesReceiver;
    private GattStateChangesReceiver      mGattStateChangesReceiver;
    private ScreenStateChangesReceiver    mScreenStateChangesReceiver;
    private IntentFilter                  mBluetoothStateFilter;
    private IntentFilter                  mGattStateFilter;
    private IntentFilter                  mScreenStateFilter;

    /****************************************************
     * INITIALIZATION
     ***************************************************/

    // TODO later...

    /****************************************************
     * BLUETOOTH MANAGER AND ADAPTER AND STATES
     ***************************************************/

    private boolean initAdapter()
    {
        try
        {
            if (mManager == null)
            {
                mManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);

                if (mManager == null)
                {
                    // DEBUG
                    Log.d(TAG, "Unable to initialize BluetoothManager.");

                    return false;
                }
            }

            mAdapter = mManager.getAdapter();

            if (mAdapter == null)
            {
                // DEBUG
                Log.d(TAG, "Unable to get BluetoothAdapter.");

                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error while Initialize Bluetooth LE.", e);

            return false;
        }
    }

    private void start()
    {
        // DEBUG
        Log.d(TAG, "start()");

        // Is bluetooth supported?
        if (Utils.isBluetothHardwareAvailable(mContext))
        {
            // Is bluetooth le supported?
            if (Utils.isBluetothLEFeatureAvailable(mContext))
            {
                // Is bluetooth on or off?
                if (Utils.isBluetothEnabled(mContext))
                {
                    if (initAdapter())
                    {
                        if (Utils.isPeripheralSupported(mAdapter))
                        {
                            mClient.setScanner();
                            goToState(BLEState.SCANNING);
                        }
                        else
                        {
                            // DEBUG
                            Log.d(TAG, "BLE Peripheral mode isn't supported on this devise.");
                        }
                    }
                    else
                    {
                        goToState(BLEState.SLEEP);
                    }
                }
                else
                {
                    goToState(BLEState.SLEEP);
                }
            }
            else
            {
                goToState(BLEState.SLEEP);
            }
        }
        else
        {
            goToState(BLEState.SLEEP);
        }
    }

    public void goToState(BLEState state)
    {
        if (state.equals(this.mState))
        {
            // DEBUG
            Log.d(TAG, "We are already in same state.");

            return;
        }

        if (mManager == null || mAdapter == null)
        {
            // DEBUG
            Log.d(TAG, "Error while Initialize Bluetooth LE.");

            return;
        }

        if (state.equals(BLEState.SLEEP))
        {
            mClient.stopScan();
            mClient.disconnect();

            mAdapter = null;
            mManager = null;

            setState(BLEState.SLEEP);

            return;
        }
        else if (state.equals(BLEState.IDLE))
        {
            mClient.stopScan();

            setState(BLEState.IDLE);

            return;
        }
        else if (state.equals(BLEState.SEARCHING))
        {
            mClient.stopScan();
            mClient.startScan();

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if (!BLEManager.this.mState.equals(BLEState.CONNECTED))
                    {
                        goToState(BLEState.IDLE);
                    }
                }
            }, 60000);

            setState(BLEState.SEARCHING);

            return;
        }
        else if (state.equals(BLEState.CONNECTING))
        {
            mClient.stopScan();

            mClient.connect(mDevice);

            setState(BLEState.CONNECTING);

            return;
        }
        else if (state.equals(BLEState.CONNECTED))
        {
            setState(BLEState.CONNECTED);

            // Set as in-relationship
            // TODO later...

            return;
        }
        else if (state.equals(BLEState.SCANNING))
        {
            // If is not in relationship,
            // go to IDLE state and retun
            // TODO later...

            mClient.stopScan();
            mClient.startScan();

            setState(BLEState.SCANNING);

            return;
        }
        else if (state.equals(BLEState.STAND_BY))
        {
            mClient.stopScan();

            setState(BLEState.STAND_BY);

            return;
        }
    }

    /****************************************************
     * FUNCTIONALITY
     ***************************************************/

    public void disconnect()
    {
        // Set as single
        // TODO later...

        if (!this.mState.equals(BLEState.CONNECTED))
        {
            // DEBUG
            Log.d(TAG, "Can not disconnect. We are not connected.");

            return;
        }

        mClient.disconnect();
    }

    /****************************************************
     * Getters & Setters
     ***************************************************/

    public BLEState getState()
    {
        return mState;
    }

    public void setState(BLEState state)
    {
        // DEBUG
        Log.d(TAG, "BLE State: " + state.toString());

        this.mState = state;

        Broadcast.notifyBLEStateChange(mContext);
    }

    /****************************************************
     * INNER CLASSES
     ***************************************************/

    class BluetoothStateChangesReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
            {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state)
                {
                    case BluetoothAdapter.STATE_OFF:
                        // DEBUG
                        Log.d(TAG, "BluetoothAdapter.STATE_OFF");

                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        // DEBUG
                        Log.d(TAG, "BluetoothAdapter.STATE_TURNING_OFF");

                        // Clean up
                        goToState(BLEState.SLEEP);
                        break;

                    case BluetoothAdapter.STATE_ON:
                        // DEBUG
                        Log.d(TAG, "BluetoothAdapter.STATE_ON");

                        // Start
                        start();
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        // DEBUG
                        Log.d(TAG, "BluetoothAdapter.STATE_TURNING_ON");

                        break;
                }
            }
        }
    }

    class GattStateChangesReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (action.equals(Actions.ACTION_BLE_STATE_CHANGE))
            {
                // TODO later...
            }
            else if (action.equals(Actions.ACTION_GATT_CONNECTION_STATE_CHANGED))
            {
                int newState = intent.getIntExtra(Extras.NEW_STATE, BluetoothProfile.STATE_DISCONNECTED);

                if (newState == BluetoothProfile.STATE_CONNECTED)
                {
                    goToState(BLEState.CONNECTED);
                }
                else if (newState == BluetoothProfile.STATE_DISCONNECTED)
                {
                    goToState(BLEState.SCANNING);
                }
            }
            else if (action.equals(Actions.ACTION_SCAN_RESULT))
            {
                if (Build.VERSION.SDK_INT < 21)
                {
                    BluetoothDevice device     = intent.getParcelableExtra(Extras.BLUETOOTH_DEVICE);
                    int             rssi       = intent.getIntExtra(Extras.RSSI, 0);
                    byte[]          scanRecord = intent.getByteArrayExtra(Extras.SCAN_RECORD);

                    mDevice = device;
                }
                else
                {
                    ScanResult result = intent.getParcelableExtra(Extras.SCAN_RESULT);

                    mDevice = result.getDevice();
                }

                goToState(BLEState.CONNECTING);
            }
            else if (action.equals(Actions.ACTION_BATCH_SCAN_RESULTS))
            {
                List<ScanResult> results = intent.getParcelableArrayListExtra(Extras.BATCH_SCAN_RESULTS);

                if (!results.isEmpty())
                {
                    ScanResult result = results.get(0);

                    BluetoothDevice device = result.getDevice();

                    mDevice = result.getDevice();
                }

                goToState(BLEState.CONNECTING);
            }
            else if (action.equals(Actions.ACTION_SCAN_FAILED))
            {
                // TODO later...
            }
        }
    }

    class ScreenStateChangesReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();

            /**
             * We cannot catch ACTION_SCREEN_OFF and ACTION_SCREEN_ON intents through XML (?!).
             * However, we could use a Service that registers a BroadcastReceiver member in its onStartCommand()
             * and unregisters it in its onDestroy().
             * This would require the service to be running in the background, constantly or as long as you need it to,
             * so be sure to explore alternative routes.
             *
             * No trouble with ACTION_USER_PRESENT intent!
             */
            if (action.equals(Intent.ACTION_SCREEN_OFF))
            {
                // DEBUG
                Log.d(TAG, "ACTION_SCREEN_OFF");
            }
            else if (action.equals(Intent.ACTION_SCREEN_ON))
            {
                // DEBUG
                Log.d(TAG, "ACTION_SCREEN_ON");

                // Resume
                // TODO later...
            }
            else if (action.equals(Intent.ACTION_USER_PRESENT))
            {
                // DEBUG
                Log.d(TAG, "ACTION_USER_PRESENT");
            }
        }
    }

    /****************************************************
     * Lazy and Thread-safe Singleton Pattern
     ***************************************************/

    private BLEManager()
    {
        // Context
        mContext = App.getAppContext();

        // BluetoothManager & BluetoothAdapter
        initAdapter();

        // Gatt Server
        mClient = BLEClient.getInstance();
        mClient.setAdapter(mAdapter);

        /* Init receivers */

        mBluetoothStateChangesReceiver = new BluetoothStateChangesReceiver();
        mGattStateChangesReceiver = new GattStateChangesReceiver();
        mScreenStateChangesReceiver = new ScreenStateChangesReceiver();

        mBluetoothStateFilter = new IntentFilter();
        mGattStateFilter = new IntentFilter();
        mScreenStateFilter = new IntentFilter();

        mBluetoothStateFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        mGattStateFilter.addAction(Actions.ACTION_BLE_STATE_CHANGE);
        mGattStateFilter.addAction(Actions.ACTION_GATT_CONNECTION_STATE_CHANGED);
        mGattStateFilter.addAction(Actions.ACTION_SCAN_RESULT);
        mGattStateFilter.addAction(Actions.ACTION_BATCH_SCAN_RESULTS);
        mGattStateFilter.addAction(Actions.ACTION_SCAN_FAILED);

        mScreenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mScreenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        mScreenStateFilter.addAction(Intent.ACTION_USER_PRESENT);

        mContext.registerReceiver(mBluetoothStateChangesReceiver, mBluetoothStateFilter);
        mContext.registerReceiver(mGattStateChangesReceiver, mGattStateFilter);
        mContext.registerReceiver(mScreenStateChangesReceiver, mScreenStateFilter);

        // In The Name Of God
        start();
    }

    private static class SingletonHolder
    {
        private static final BLEManager INSTANCE = new BLEManager();
    }

    public static BLEManager getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

}
