package com.clexi.clexi.bluetoothle_central.consts;

import java.util.UUID;

/**
 * Created by Yousef on 2/21/2018.
 */

public class UUIDs
{

    /****************************************************
     * CLEXI SERVICE
     *
     * UUID: (B36E1E00-4B56-3886-4C48-F903401B31F8)
     *
     * Characteristics:
     *   Request:           (0x1E01), [Write],
     *   Response:          (0x1E02), [Notify],
     *   Walking Status:    (0x1E03), [Read],
     *   Event:             (0x1E04), [Notify]
     ***************************************************/

    public static final UUID UUID_SERVICE_CLEXI = UUID.fromString("B36E1E00-4B56-3886-4C48-F903401B31F8");

    public static final UUID UUID_CHARACTERISTIC_CLEXI_REQUEST        = UUID.fromString("B36E1E01-4B56-3886-4C48-F903401B31F8");
    public static final UUID UUID_CHARACTERISTIC_CLEXI_RESPONSE       = UUID.fromString("B36E1E02-4B56-3886-4C48-F903401B31F8");
    public static final UUID UUID_CHARACTERISTIC_CLEXI_WALKING_STATUS = UUID.fromString("B36E1E03-4B56-3886-4C48-F903401B31F8");
    public static final UUID UUID_CHARACTERISTIC_CLEXI_EVENT          = UUID.fromString("B36E1E04-4B56-3886-4C48-F903401B31F8");

    /****************************************************
     * BATTERY SERVICE
     *
     * UUID: (0x180F)
     *
     * Characteristics:
     *   Battery Level: (0x2A19), [Read, Notify]
     ***************************************************/

    public static final UUID UUID_SERVICE_BATTERY = UUID.fromString("0000180F-0000-1000-8000-00805F9B34FB");

    public static final UUID UUID_CHARACTERISTIC_BATTERY_LEVEL = UUID.fromString("00002A19-0000-1000-8000-00805F9B34FB");

    /****************************************************
     * DEVICE INFORMATION SERVICE
     *
     * UUID: (0x180A)
     *
     * Characteristics:
     *   Manufacturer Name String:  (0x2A29), [Read],
     *   Model Number String:       (0x2A24), [Read],
     *   Firmware Revision String:  (0x2A26), [Read]
     ***************************************************/

    public static final UUID UUID_SERVICE_DEVICE_INFORMATION = UUID.fromString("0000180A-0000-1000-8000-00805F9B34FB");

    public static final UUID UUID_CHARACTERISTIC_MANUFACTURER_NAME_STRING = UUID.fromString("00002A29-0000-1000-8000-00805F9B34FB");
    public static final UUID UUID_CHARACTERISTIC_MODEL_NUMBER_STRING      = UUID.fromString("00002A24-0000-1000-8000-00805F9B34FB");
    public static final UUID UUID_CHARACTERISTIC_FIRMWARE_REVISION_STRING = UUID.fromString("00002A26-0000-1000-8000-00805F9B34FB");

    /****************************************************
     * DESCRIPTION & CONFIGURATION
     *
     * Description UUID:    (0x2901),
     * Configuration UUID:  (0x2902)
     ***************************************************/

    public static final UUID UUID_CHARACTERISTIC_USER_DESCRIPTION     = UUID.fromString("00002901-0000-1000-8000-00805F9B34FB");
    public static final UUID UUID_CLIENT_CHARACTERISTIC_CONFIGURATION = UUID.fromString("00002902-0000-1000-8000-00805F9B34FB");

    /****************************************************
     * Utils
     ***************************************************/

    /**
     * Convert short integer UUID to complete Android parsable UUID
     *
     * @param shortIntegerUUID 16-Bit UUID in integer format
     * @return Complete 128-Bit UUID
     */
    public static UUID fromShortIntegerUUID(int shortIntegerUUID)
    {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805F9B34FBL;

        long value = shortIntegerUUID & 0xFFFFFFFF;

        return new UUID(MSB | (value << 32), LSB);
    }
}
