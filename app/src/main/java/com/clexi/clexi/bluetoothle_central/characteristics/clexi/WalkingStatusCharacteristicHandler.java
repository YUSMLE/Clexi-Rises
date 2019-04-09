package com.clexi.clexi.bluetoothle_central.characteristics.clexi;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.clexi.clexi.bluetoothle_central.characteristics.CharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

import java.util.UUID;

/**
 * Created by Yousef on 8/7/2018.
 */

public class WalkingStatusCharacteristicHandler extends CharacteristicHandler
{

    public static final String TAG  = WalkingStatusCharacteristicHandler.class.getSimpleName();
    public static final String NAME = "Walking Status Handler";

    protected static final UUID uuid        = UUIDs.UUID_CHARACTERISTIC_CLEXI_WALKING_STATUS;
    protected static final int  properties  = BluetoothGattCharacteristic.PROPERTY_READ;
    protected static final int  permissions = BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM;

    /**
     * Simulate a new BluetoothGattCharacteristic.
     */
    public WalkingStatusCharacteristicHandler(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
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
        // TODO later...
    }

    @Override
    public void write(byte[] value)
    {
        // Nothing
    }

    @Override
    public void onRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        // TODO later...

        operateNext();
    }

    @Override
    public void onWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        // Nothing
    }
}
