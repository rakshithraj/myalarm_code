package com.example.myalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.example.myalarm.BroadcastReceiver.AlarmReceiver;
import com.example.myalarm.dao.AlamInfo;
import com.example.myalarm.dao.AlarmInfiList;

/**
 * Created by Rakshith on 7/13/2015.
 */
public class AddAlarmActivity extends Activity implements View.OnClickListener{
    DatePicker datePicker;
    TimePicker timePicker;
    Button btDone;
    AlamInfo alamInfo;
    AlarmInfiList alarmInfiList;
    //AlarmManager alarmManager;
    //private PendingIntent pendingIntent;
    private static AddAlarmActivity inst;
    int count=0;
    ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
    public static AddAlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.add_alarm);
        intialize();
         alarmInfiList=new AlarmInfiList();
        alarmInfiList= alarmInfiList.DeSerialize(this);
        if(alarmInfiList==null)
            alarmInfiList=new AlarmInfiList();


    }

    private void intialize() {
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        btDone = (Button)findViewById(R.id.btDone);
        btDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btDone:
                String date_time="";
                int month=datePicker.getMonth()+1;
                int hour=timePicker.getCurrentHour();
                int min=timePicker.getCurrentMinute();
                String am_pm="am";

                if(hour>=12){
                    if(min>0){
                        hour=hour-12;
                        am_pm="pm";
                    }
                }
                date_time=datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth()+" "+hour+":"+min+":00 "+am_pm;
                alamInfo=new AlamInfo();
                setAlarm();
                savedAlarm();
                finish();
                break;
        }
    }

    private void savedAlarm() {

        alamInfo.setDate(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
        alamInfo.setTime(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());

        alarmInfiList.getAlamInfoList().add(alamInfo);
        alarmInfiList.Serialize(this);

    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(),
                00);
        int alarmId=randInt(0,100);
        Intent myIntent = new Intent(this, AlarmReceiver.class);
        myIntent.putExtra("Id",alarmId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddAlarmActivity.this,alarmId, myIntent, 0);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        alamInfo.setId(alarmId);
        intentArray.add(pendingIntent);
        Toast.makeText(this,"alram set",Toast.LENGTH_SHORT).show();

    }
    public void setAlarmText(String alarmText) {
        Toast.makeText(this,"helo",Toast.LENGTH_LONG).show();
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
