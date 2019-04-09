package com.clexi.clexi.bluetoothle_central;

/**
 * Created by Yousef on 4/3/2018.
 */

public enum BLEState
{
    SLEEP,      // Bluetooth is off, or BLE (Peripheral mode) unsupported.
    IDLE,       // No already paired device to establish connection.
    SEARCHING,  // Scanning to pair with new device.
    CONNECTING, // Connecting with device.
    CONNECTED,  // Connected with device.
    SCANNING,   // Scanning to connect with already paired device.
    STAND_BY    // Paired device is unavailable.
}
