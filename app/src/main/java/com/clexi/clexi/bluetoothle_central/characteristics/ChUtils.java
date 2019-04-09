package com.clexi.clexi.bluetoothle_central.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

import com.clexi.clexi.bluetoothle_central.characteristics.clexi.EventCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.clexi.RequestCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.clexi.ResponseCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.clexi.WalkingStatusCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Yousef on 8/7/2018.
 */

public class ChUtils
{

    public static final String TAG = ChUtils.class.getSimpleName();

    public static BluetoothGattCharacteristic createNotificationCharacteristic(UUID uuid)
    {
        // Notification Characteristic
        BluetoothGattCharacteristic notificationCharacteristic = new BluetoothGattCharacteristic(
                uuid,
                0,
                0
        );

        return notificationCharacteristic;
    }

    /**
     * Get name of characteristic by UUID
     *
     * Find the corresponded characteristic class by given UUID,
     * and return its name.
     *
     * @param uuid
     * @return Name of haracteristic class
     */
    public static String getNameById(UUID uuid)
    {
        if (uuid == null)
        {
            // Be better if throw the related exception!
            // Anyway...

            return null;
        }

        if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_REQUEST))
        {
            return RequestCharacteristicHandler.NAME;
        }
        else if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_RESPONSE))
        {
            return ResponseCharacteristicHandler.NAME;
        }
        else if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_WALKING_STATUS))
        {
            return WalkingStatusCharacteristicHandler.NAME;
        }
        else if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_EVENT))
        {
            return EventCharacteristicHandler.NAME;
        }
        else
        {
            return "UNKNOWN_CHARACTERISTIC";
        }
    }

    /**
     * Get name of configuration by value
     *
     * @param value
     * @return
     */
    public static String getNameOfConfiguration(byte[] value)
    {
        if (value == null)
        {
            // Be better if throw the related exception!
            // Anyway...

            return null;
        }

        if (Arrays.equals(value, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE))
        {
            return "ENABLE_NOTIFICATION_VALUE";
        }
        else if (Arrays.equals(value, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE))
        {
            return "ENABLE_INDICATION_VALUE";
        }
        else if (Arrays.equals(value, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
        {
            return "DISABLE_NOTIFICATION_VALUE";
        }
        else
        {
            return "UNKNOWN_VALUE";
        }
    }
}
