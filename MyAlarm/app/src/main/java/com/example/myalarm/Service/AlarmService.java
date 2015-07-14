package com.example.myalarm.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.myalarm.AddAlarmActivity;
import com.example.myalarm.AlarmRingActivity;
import com.example.myalarm.R;

/**
 * Created by Rakshith on 7/13/2015.
 */
public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Wake Up! Wake Up!",intent.getIntExtra("Id",-1));
    }

    private void sendNotification(String msg,int ID) {
       /* Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AddAlarmActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");*/


        Intent inetnt=new Intent(this,AlarmRingActivity.class);
        inetnt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        inetnt.putExtra("Id",ID);
        this.startActivity(inetnt);
    }
}