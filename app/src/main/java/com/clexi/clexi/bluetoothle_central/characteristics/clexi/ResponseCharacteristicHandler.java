package com.clexi.clexi.bluetoothle_central.characteristics.clexi;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.clexi.clexi.bluetoothle_central.characteristics.CharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

import java.util.UUID;

/**
 * Created by Yousef on 8/7/2018.
 */

public class ResponseCharacteristicHandler extends CharacteristicHandler
{

    public static final String TAG  = ResponseCharacteristicHandler.class.getSimpleName();
    public static final String NAME = "Response Handler";

    protected static final UUID uuid        = UUIDs.UUID_CHARACTERISTIC_CLEXI_RESPONSE;
    protected static final int  properties  = BluetoothGattCharacteristic.PROPERTY_NOTIFY;
    protected static final int  permissions = 0;

    /**
     * Simulate a new BluetoothGattCharacteristic.
     */
    public ResponseCharacteristicHandler(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        super(uuid, properties, permissions);

        setGatt(gatt);
        setCharacteristic(characteristic);

        enableNotification(true);
    }

    @Override
    public void onNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        // TODO later...

        operateNext();
    }

    @Override
    public void read()
    {
        // Nothing
    }

    @Override
    public void write(byte[] value)
    {
        // Nothing
    }

    @Override
    public void onRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        // Nothing
    }

    @Override
    public void onWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
    {
        // Nothing
    }
}
