package com.clexi.clexi.bluetoothle_central.framing;

/**
 * Created by Yousef on 8/24/2017.
 */

public abstract class AbstractPacket
{

    public static final String TAG = AbstractPacket.class.getSimpleName();

    protected byte[] data;

    /**
     * Empty Constructor
     */
    public AbstractPacket()
    {
        // Nothing
    }

    public AbstractPacket(byte[] data)
    {
        this.data = data;
    }

    public abstract void pullFrom(byte[] packetData);

    public abstract byte[] pushTo();

    /****************************************************
     * Getters & Setters
     ***************************************************/

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }
}
