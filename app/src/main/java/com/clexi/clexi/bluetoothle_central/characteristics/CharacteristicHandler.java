package com.clexi.clexi.bluetoothle_central.characteristics;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.clexi.clexi.app.Log;

import com.clexi.clexi.bluetoothle_central.consts.UUIDs;
import com.clexi.clexi.bluetoothle_central.framing.Packet;
import com.clexi.clexi.bluetoothle_central.queue.GattOperation;
import com.clexi.clexi.bluetoothle_central.queue.GattOperationQueue;
import com.clexi.clexi.helper.Converter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Yousef on 5/14/2018.
 */

public abstract class CharacteristicHandler
{

    public static final String TAG = CharacteristicHandler.class.getSimpleName();

    protected UUID uuid;
    protected int  properties;
    protected int  permissions;

    protected BluetoothGatt               gatt;
    protected BluetoothGattCharacteristic characteristic;
    protected GattOperationQueue          queue;

    /**
     * Simulate a new BluetoothGattCharacteristic.
     *
     * @param uuid        The UUID for this characteristic
     * @param properties  Properties of this characteristic
     * @param permissions Permissions for this characteristic
     */
    public CharacteristicHandler(UUID uuid, int properties, int permissions)
    {
        this.uuid = uuid;
        this.properties = properties;
        this.permissions = permissions;

        queue = new GattOperationQueue();
    }

    public BluetoothGatt getGatt()
    {
        return gatt;
    }

    public void setGatt(BluetoothGatt gatt)
    {
        this.gatt = gatt;
    }

    public BluetoothGattCharacteristic getCharacteristic()
    {
        return characteristic;
    }

    public void setCharacteristic(BluetoothGattCharacteristic characteristic)
    {
        this.characteristic = characteristic;
    }

    /****************************************************
     * Gatt Operators
     ***************************************************/

    protected void operateThis(GattOperation gattOperation)
    {
        GattOperation queuedOperation = queue.addToQueue(gattOperation);

        if (queuedOperation != null)
        {
            operate(queuedOperation);
        }
    }

    protected void operateNext()
    {
        GattOperation queuedOperation = queue.getFromQueue();

        if (queuedOperation != null)
        {
            operate(queuedOperation);
        }
    }

    private void operate(GattOperation gattOperation)
    {
        switch (gattOperation.type)
        {
            case ReadCharacteristic:
                gatt.readCharacteristic(characteristic);
                break;

            case WriteCharacteristic:
                // DEBUG
                Log.d(TAG, "Value to write: " + Converter.encodeToHexadecimal(gattOperation.value));

                characteristic.setValue(gattOperation.value);
                gatt.writeCharacteristic(characteristic);
                break;

            case ReadDescriptor:
                // TODO later...
                break;

            case WriteDescriptor:
                // TODO later...
                gatt.readCharacteristic(characteristic);
                break;

            default:
                // Nothing
        }
    }

    /****************************************************
     * Abstract Methods To Implement
     ***************************************************/

    public abstract void onNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);

    public abstract void read();

    public abstract void write(byte[] value);

    public abstract void onRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

    public abstract void onWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

    /****************************************************
     * Notification
     ***************************************************/

    protected void enableNotification(boolean enabled)
    {
        if (gatt != null && characteristic != null)
        {
            // Enable Notification
            gatt.setCharacteristicNotification(characteristic, enabled);

            // Write Configuration
            writeConfiguration(
                    gatt,
                    characteristic,
                    enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
            );
        }
        else
        {
            // TODO: handle this state...
        }
    }

    /****************************************************
     * Tools
     ***************************************************/

    protected byte[] mergeData(ArrayList<Packet> packets)
    {
        byte[] totalData = new byte[0];

        // TODO later...

        return totalData;
    }

    protected ArrayList<Packet> splitData(byte[] totalData, byte type, byte cmd)
    {
        ArrayList<Packet> packets = new ArrayList<Packet>();

        // TODO later...

        return packets;
    }

    public static void readConfiguration(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        BluetoothGattDescriptor configuration = characteristic.getDescriptor(UUIDs.UUID_CLIENT_CHARACTERISTIC_CONFIGURATION);

        if (configuration != null)
        {
            gatt.readDescriptor(configuration);
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public static void writeConfiguration(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value)
    {
        BluetoothGattDescriptor configuration = characteristic.getDescriptor(UUIDs.UUID_CLIENT_CHARACTERISTIC_CONFIGURATION);

        if (configuration != null)
        {
            configuration.setValue(value);
            gatt.writeDescriptor(configuration);
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public static void readDescription(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        BluetoothGattDescriptor description = characteristic.getDescriptor(UUIDs.UUID_CHARACTERISTIC_USER_DESCRIPTION);

        if (description != null)
        {
            gatt.readDescriptor(description);
        }
        else
        {
            // TODO: handle this state...
        }
    }

    public static void writeDescription(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value)
    {
        BluetoothGattDescriptor description = characteristic.getDescriptor(UUIDs.UUID_CHARACTERISTIC_USER_DESCRIPTION);

        if (description != null)
        {
            description.setValue(value);
            gatt.writeDescriptor(description);
        }
        else
        {
            // TODO: handle this state...
        }
    }

}
