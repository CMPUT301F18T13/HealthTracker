package com.example.healthtracker.EntityObjects;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.healthtracker.Activities.AddReminderActivity;
import com.example.healthtracker.Activities.LoginActivity;
import com.example.healthtracker.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("!!!!!!!!!!! :","RUN1");
        //Get id & mode from intent.
        int notificationId = intent.getIntExtra("notificationID",0);
        String message = intent.getStringExtra("todo");

        // When notification is tapped, call Activity.
        Intent mainIntent = new Intent(context, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Prepare notification.
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("It's time to take photo!!!!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);

        //Notify
        Log.d("!!!!!!!!!!! :","RUN2");
        myNotificationManager.notify(notificationId,builder.build());



    }
}
