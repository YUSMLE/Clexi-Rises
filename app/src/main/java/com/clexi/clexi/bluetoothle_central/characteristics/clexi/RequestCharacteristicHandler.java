package com.clexi.clexi.bluetoothle_central.characteristics.clexi;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.clexi.clexi.bluetoothle_central.characteristics.CharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

import java.util.UUID;

/**
 * Created by Yousef on 5/22/2018.
 */

public class RequestCharacteristicHandler extends CharacteristicHandler
{

    public static final String TAG  = RequestCharacteristicHandler.class.getSimpleName();
    public static final String NAME = "Request Handler";

    protected static final UUID uuid        = UUIDs.UUID_CHARACTERISTIC_CLEXI_REQUEST;
    protected static final int  properties  = BluetoothGattCharacteristic.PROPERTY_WRITE;
    protected static final int  permissions = BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM;

    /**
     * Simulate a new BluetoothGattCharacteristic.
     */
    public RequestCharacteristicHandler(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        super(uuid, properties, permissions);

        setGatt(gatt);
        setCharacteristic(characteristic);
    }

    @Override
    public void onNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        // Nothing
    }

    @Override
    public void read()
    {
        // Nothing
    }

    @Override
    public void write(byte[] value)
    {
        // TODO later...
    }

    @Override
    public void onRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        // Nothing
    }

    @Override
    public void onWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        // TODO later...

        operateNext();
    }
}
