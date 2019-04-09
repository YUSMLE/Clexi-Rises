package com.clexi.clexi.bluetoothle_central.framing;

/**
 * User: Hamidreza
 * Date: 5/13/15
 * Time: 5:35 PM
 */

public class Packet extends AbstractPacket
{

    public static final int TOTAL_VOLUME = 20;
    public static final int DATA_LENGHT  = TOTAL_VOLUME - 4;

    // Bluetooth Packet Types
    public static final byte TYPE_FIDO = 0;

    private byte  type;
    private byte  cmd;
    private short reserved;

    /**
     * Empty Constructor
     */
    public Packet()
    {
        // Nothing
    }

    public Packet(byte[] data, byte type, byte cmd, short reserved)
    {
        super(data);

        this.type = type;
        this.cmd = cmd;
        this.reserved = reserved;
    }

    @Override
    public void pullFrom(byte[] packetData)
    {
        type = packetData[0];
        cmd = packetData[1];
        reserved = (short) (packetData[2] | (packetData[3] << 8));
        data = new byte[DATA_LENGHT];
        System.arraycopy(packetData, 4, data, 0, DATA_LENGHT);
    }

    @Override
    public byte[] pushTo()
    {
        byte[] packetData = new byte[TOTAL_VOLUME];
        packetData[0] = type;
        packetData[1] = cmd;
        packetData[2] = (byte) (reserved % 256);
        packetData[3] = (byte) (reserved / 256);
        System.arraycopy(data, 0, packetData, 4, DATA_LENGHT);

        return packetData;
    }

    /****************************************************
     * Getters & Setters
     ***************************************************/

    public byte getType()
    {
        return type;
    }

    public void setType(byte type)
    {
        this.type = type;
    }

    public byte getCmd()
    {
        return cmd;
    }

    public void setCmd(byte cmd)
    {
        this.cmd = cmd;
    }

    public short getReserved()
    {
        return reserved;
    }

    public void setReserved(short reserved)
    {
        this.reserved = reserved;
    }
}
