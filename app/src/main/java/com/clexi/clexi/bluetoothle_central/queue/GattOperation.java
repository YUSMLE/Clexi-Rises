package com.clexi.clexi.bluetoothle_central.queue;

/**
 * Created by yousef on 8/10/2016.
 * <p>
 * An enqueueable write operation -
 * characteristic read,
 * characteristic write,
 * descriptor read or
 * descriptor write
 */

public class GattOperation
{

    public GattOperationType type;
    public byte[]            value; // Only used for characteristic/descriptor write
}
