package com.clexi.clexi.bluetoothle_central;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import com.clexi.clexi.app.Log;

import com.clexi.clexi.app.App;
import com.clexi.clexi.bluetoothle_central.characteristics.ChUtils;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

/**
 * Created by yousef on 9/7/2016.
 */

public class GattCallback extends BluetoothGattCallback
{
    public static final String TAG = GattCallback.class.getSimpleName();

    private Context mContext;

    /**
     * Constructor
     */
    public GattCallback()
    {
        this.mContext = App.getAppContext();
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
    {
        super.onConnectionStateChange(gatt, status, newState);

        // DEBUG
        Log.d(TAG, "Our gatt client connection state changed, new state: " + Integer.toString(newState));

        switch (newState)
        {
            case BluetoothProfile.STATE_CONNECTED:
                // DEBUG
                Log.d(TAG, "Connection State: STATE_CONNECTED");

                // Nothing
                break;

            case BluetoothProfile.STATE_DISCONNECTED:
                // DEBUG
                Log.d(TAG, "Connection State: STATE_DISCONNECTED");

                // Nothing
                break;

            case BluetoothProfile.STATE_CONNECTING:
                // DEBUG
                Log.d(TAG, "Connection State: STATE_CONNECTING");

                // Nothing
                break;

            case BluetoothProfile.STATE_DISCONNECTING:
                // DEBUG
                Log.d(TAG, "Connection State: STATE_DISCONNECTING");

                // Nothing
                break;

            default:
                // Nothing
        }

        // Broadcast Gatt State
        Broadcast.notifyConnectionStateChange(mContext, status, newState);
    }

    @Override
    public void onServicesDiscovered(final BluetoothGatt gatt, int status)
    {
        super.onServicesDiscovered(gatt, status);

        // DEBUG
        Log.d(TAG, "onServicesDiscovered");

        // Broadcast Gatt State
        Broadcast.notifyServicesDiscovered(mContext, status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        super.onCharacteristicChanged(gatt, characteristic);

        // DEBUG
        Log.d(TAG, "onCharacteristicChanged");

        // Process the notification
        processNotification(gatt, characteristic);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        super.onCharacteristicRead(gatt, characteristic, status);

        // DEBUG
        Log.d(TAG, "onCharacteristicRead");

        // Distribute received read callback
        BLEClient.getInstance().getHandler(characteristic).onRead(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        super.onCharacteristicWrite(gatt, characteristic, status);

        // DEBUG
        Log.d(TAG, "onCharacteristicWrite");

        // Distribute received write callback
        BLEClient.getInstance().getHandler(characteristic).onWrite(gatt, characteristic, status);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status)
    {
        super.onDescriptorRead(gatt, descriptor, status);

        String characteristicName = ChUtils.getNameById(descriptor.getCharacteristic().getUuid());

        if (descriptor.getUuid().equals(UUIDs.UUID_CLIENT_CHARACTERISTIC_CONFIGURATION))
        {
            // DEBUG
            Log.d(TAG, "onConfigurationRead");
            Log.d(TAG, String.format("Read from configurator of \'%s\' characteristic.", characteristicName));
            Log.d(TAG, String.format("Read value: %s", ChUtils.getNameOfConfiguration(descriptor.getValue())));
        }
        else if (descriptor.getUuid().equals(UUIDs.UUID_CHARACTERISTIC_USER_DESCRIPTION))
        {
            // DEBUG
            Log.d(TAG, "onDescriptionRead");
            Log.d(TAG, String.format("Read from descriptor of \'%s\' characteristic.", characteristicName));
            Log.d(TAG, String.format("Read value: %s", new String(descriptor.getValue())));
        }

        // TODO later...
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status)
    {
        super.onDescriptorWrite(gatt, descriptor, status);

        String characteristicName = ChUtils.getNameById(descriptor.getCharacteristic().getUuid());

        if (descriptor.getUuid().equals(UUIDs.UUID_CLIENT_CHARACTERISTIC_CONFIGURATION))
        {
            // DEBUG
            Log.d(TAG, "onConfigurationWrite");
            Log.d(TAG, String.format("Write to configurator of \'%s\' characteristic.", characteristicName));
            Log.d(TAG, String.format("Written value: %s", ChUtils.getNameOfConfiguration(descriptor.getValue())));
        }
        else if (descriptor.getUuid().equals(UUIDs.UUID_CHARACTERISTIC_USER_DESCRIPTION))
        {
            // DEBUG
            Log.d(TAG, "onDescriptionWrite");
            Log.d(TAG, String.format("Write to descriptor of \'%s\' characteristic.", characteristicName));
            Log.d(TAG, String.format("Written value: %s", new String(descriptor.getValue())));
        }

        // TODO later...
    }

    @Override
    public void onReadRemoteRssi(final BluetoothGatt gatt, int rssi, int status)
    {
        super.onReadRemoteRssi(gatt, rssi, status);

        // DEBUG
        Log.d(TAG, "onReadRemoteRssi: " + rssi);

        // Broadcast Gatt State
        Broadcast.notifyReadRemoteRssi(mContext, rssi, status);
    }

    /****************************************************
     * Processes
     ***************************************************/

    private void processNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        // TODO later...
    }

    /****************************************************
     * Interface
     ***************************************************/

    // Nothing

}
