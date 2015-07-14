package com.example.myalarm.BroadcastReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myalarm.dao.AlarmInfiList;

import java.util.Calendar;

/**
 * Created by Rakshith on 7/14/2015.
 */
public class NotificationDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int alarmId = intent.getExtras().getInt("Id");


        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,alarmId, myIntent, 0);
        alarmManager.cancel(pendingIntent);
        deleteAlarmFromList(context,alarmId);
        Toast.makeText(context,"alarm dismissed",Toast.LENGTH_SHORT).show();
    }

    private void deleteAlarmFromList(Context context,  int alarmId) {
        AlarmInfiList alarmInfiList=new AlarmInfiList();
        alarmInfiList= alarmInfiList.DeSerialize(context);
        if(alarmInfiList==null)
            alarmInfiList=new AlarmInfiList();
        for(int i=0;i<alarmInfiList.getAlamInfoList().size();i++){
            if(alarmInfiList.getAlamInfoList().get(i).getId()==alarmId){
                alarmInfiList.getAlamInfoList().remove(i);
                alarmInfiList.Serialize(context);
                break;
            }
        }


    }
}