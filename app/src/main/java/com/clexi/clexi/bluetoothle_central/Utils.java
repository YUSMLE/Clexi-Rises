package com.clexi.clexi.bluetoothle_central;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.clexi.clexi.app.Log;

import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Yousef on 6/17/2017.
 */

public class Utils
{

    public static final String TAG = Utils.class.getSimpleName();

    public static boolean isBluetothHardwareAvailable(Context context)
    {
        // Check general bluetooth hardware
        final BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        if (manager == null)
        {
            // DEBUG
            Log.d(TAG, "Unable to initialize BluetoothManager.");

            return false;
        }

        // Get adapter from manager
        final BluetoothAdapter adapter = manager.getAdapter();

        if (adapter == null)
        {
            // DEBUG
            Log.d(TAG, "Unable to get BluetoothAdapter.");

            return false;
        }

        return true;
    }

    public static boolean isBluetothLEFeatureAvailable(Context context)
    {
        // Check if BluetoothLE is also available
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public static boolean isPeripheralSupported(BluetoothAdapter adapter)
    {
        // Check if advertising is hardware supported before accessing the BluetoothLeAdvertiser.
        return adapter.isMultipleAdvertisementSupported();
    }

    public static boolean isBluetothEnabled(Context context)
    {
        final BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        if (manager == null)
        {
            // DEBUG
            Log.d(TAG, "Unable to initialize BluetoothManager.");

            return false;
        }

        final BluetoothAdapter adapter = manager.getAdapter();

        if (adapter == null)
        {
            // DEBUG
            Log.d(TAG, "Unable to get BluetoothAdapter.");

            return false;
        }

        return adapter.isEnabled();
    }

    public static void enableBluetoth(Activity activity, int requestCode)
    {
        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBTIntent, requestCode);
    }

    public static boolean refreshDeviceCache(BluetoothGatt gatt)
    {
        try
        {
            BluetoothGatt localBluetoothGatt = gatt;
            Method        localMethod        = localBluetoothGatt.getClass().getMethod("refresh");

            if (localMethod != null)
            {
                return ((Boolean) localMethod.invoke(localBluetoothGatt)).booleanValue();
            }
        }
        catch (Exception localException)
        {
            Log.e(TAG, "Error while Refreshing Gatt Device Cache", localException);
        }

        return false;
    }

    public static List<BluetoothDevice> getPairedDevices()
    {
        // Get paired devices
        BluetoothAdapter     bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // Or get it from BluetoothManager instance
        Set<BluetoothDevice> pairedDevices    = bluetoothAdapter.getBondedDevices();

        // Cast Set* to List*
        List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
        devices.addAll(pairedDevices);

        return devices;
    }

    public static List<BluetoothDevice> getConnectedDevices(Context context)
    {
        // Get connected devices
        BluetoothManager      bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        List<BluetoothDevice> connectedDevices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);

        return connectedDevices;
    }

    public static boolean isConnectedDevice(Context context, BluetoothDevice bluetoothDevice)
    {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        int              connectionState  = bluetoothManager.getConnectionState(bluetoothDevice, BluetoothProfile.GATT);

        return connectionState == BluetoothProfile.STATE_CONNECTED;
    }

    public static boolean discoverDevices()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // Or get it from BluetoothManager instance

        if (bluetoothAdapter.isDiscovering())
        {
            bluetoothAdapter.cancelDiscovery();
        }

        return bluetoothAdapter.startDiscovery();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void createBond(BluetoothGatt gatt)
    {
        try
        {
            gatt.getDevice().createBond();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            // Unable to get device from this BluetoothGatt instance;
            // Maybe gatt is null,
            // or can't get device from it.
            // TODO later...
        }
    }

    public static String getAddress()
    {
        return BluetoothAdapter.getDefaultAdapter().getAddress();
    }

    public static String getBluetoothAddress(Context context)
    {
        String bluetoothAddress = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                "bluetooth_address"
        );

        // DEBUG
        Log.d(TAG, "Bluetooth Address: " + bluetoothAddress);

        return bluetoothAddress;
    }

    public static String getBluetoothAddressViaReflection()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Object bluetoothManagerService = new Mirror().on(bluetoothAdapter).get().field("mService");

        if (bluetoothManagerService == null)
        {
            // DEBUG
            Log.d(TAG, "Couldn't find BluetoothManagerService");

            return null;
        }

        Object address = new Mirror().on(bluetoothManagerService).invoke().method("getAddress").withoutArgs();

        if (address != null && address instanceof String)
        {
            // DEBUG
            Log.d(TAG, "Using reflection to get the BT Address: " + address);

            return (String) address;
        }
        else
        {
            return null;
        }
    }
}
