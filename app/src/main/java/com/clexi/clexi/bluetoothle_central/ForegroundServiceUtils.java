package com.clexi.clexi.bluetoothle_central;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.clexi.clexi.R;

/**
 * Created by Yousef on 4/28/2018.
 */

public class ForegroundServiceUtils
{

    public static final String TAG = ForegroundServiceUtils.class.getSimpleName();

    public static final String CHANNEL_ID = "CHANNEL_ID";

    public static final int NOTIFICATION_ID = 12345678;


    /**
     * Foreground Service Aproach
     */
    public static void startServiceAsForeground(Service service)
    {
        service.startForeground(NOTIFICATION_ID, ForegroundServiceUtils.getNotification(service));
    }

    /**
     * Returns the {@link NotificationManager} used as part of the foreground service.
     */
    public static NotificationManager getNotificationManager(Service service)
    {
        NotificationManager notificationManager = (NotificationManager) service.getSystemService(service.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = service.getString(R.string.app_name);

            // Create the channel for the notification.
            NotificationChannel mChannel = new NotificationChannel(ForegroundServiceUtils.CHANNEL_ID, name, NotificationManager.IMPORTANCE_NONE);

            // Set the Notification Channel for the Notification Manager.
            notificationManager.createNotificationChannel(mChannel);
        }

        return notificationManager;
    }

    /**
     * Returns the {@link NotificationCompat} used as part of the foreground service.
     */
    public static Notification getNotification(Service service)
    {
        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Notification.Builder builder = new Notification.Builder(service, CHANNEL_ID)
                    .setContentTitle(service.getString(R.string.app_name))
                    .setContentText(service.getString(R.string.app_name))
                    .setAutoCancel(true);

            notification = builder.build();
        }
        else
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(service)
                    .setContentTitle(service.getString(R.string.app_name))
                    .setContentText(service.getString(R.string.app_name))
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setAutoCancel(true);

            notification = builder.build();
        }

        return notification;
    }
}
