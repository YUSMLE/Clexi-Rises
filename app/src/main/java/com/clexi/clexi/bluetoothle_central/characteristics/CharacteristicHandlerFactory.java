package com.clexi.clexi.bluetoothle_central.characteristics;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.clexi.clexi.bluetoothle_central.characteristics.clexi.EventCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.clexi.RequestCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.clexi.ResponseCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.characteristics.clexi.WalkingStatusCharacteristicHandler;
import com.clexi.clexi.bluetoothle_central.consts.UUIDs;

import java.util.UUID;

/**
 * Created by Yousef on 8/7/2018.
 */

public class CharacteristicHandlerFactory
{

    public static final String TAG = CharacteristicHandlerFactory.class.getSimpleName();

    public static CharacteristicHandler getCharacteristicHandler(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
    {
        CharacteristicHandler characteristicHandler = null;

        UUID uuid = characteristic.getUuid();

        if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_REQUEST))
        {
            characteristicHandler = new RequestCharacteristicHandler(gatt, characteristic);
        }
        else if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_RESPONSE))
        {
            characteristicHandler = new ResponseCharacteristicHandler(gatt, characteristic);
        }
        else if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_WALKING_STATUS))
        {
            characteristicHandler = new WalkingStatusCharacteristicHandler(gatt, characteristic);
        }
        else if (uuid.equals(UUIDs.UUID_CHARACTERISTIC_CLEXI_EVENT))
        {
            characteristicHandler = new EventCharacteristicHandler(gatt, characteristic);
        }
        else
        {
            // Nothing
        }

        return characteristicHandler;
    }
}
