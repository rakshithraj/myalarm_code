package com.example.myalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myalarm.BroadcastReceiver.AlarmReceiver;
import com.example.myalarm.BroadcastReceiver.NotificationDismissedReceiver;
import com.example.myalarm.MotionDetection.AccelerometerListener;
import com.example.myalarm.MotionDetection.AccelerometerManager;
import com.example.myalarm.dao.AlarmInfiList;

import java.util.Calendar;


/**
 * Created by Rakshith on 7/13/2015.
 */
public class AlarmRingActivity extends Activity implements View.OnClickListener, AccelerometerListener {
    Dialog confirm_dialog;
    Button btCancel,btSnooze;
    TextView tvMessage;
    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;
    int Id;
    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         Id=this.getIntent().getIntExtra("Id", -1);
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         mp = MediaPlayer.create(getApplicationContext(), alarm);

        this.getWindow().setFlags(

                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,

                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (AccelerometerManager.isSupported(this)) {

            // Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }

        confirmDialog( );
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AccelerometerManager.isListening()) {

            // Start Accelerometer Listening
            AccelerometerManager.stopListening();
        }


        if(mp.isPlaying())
        mp.stop();


        if(confirm_dialog.isShowing()){
            AlarmInfiList alarmInfiList=new AlarmInfiList();
            alarmInfiList= alarmInfiList.DeSerialize(this);
            if(alarmInfiList==null)
                alarmInfiList=new AlarmInfiList();
            alarmInfiList.getAlamInfoList().remove(0);
            alarmInfiList.Serialize(this);
            confirm_dialog.setCancelable(false);
            confirm_dialog.dismiss();
            finish();
        }



    }


    public  void confirmDialog( ) {

        playTone();
        confirm_dialog = new Dialog(this);
        confirm_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        confirm_dialog.setContentView(R.layout.custom_alram_dialog);
        confirm_dialog.setCancelable(false);
        tvMessage=(TextView) confirm_dialog.findViewById(R.id.tvMessage);
        btSnooze=(Button) confirm_dialog.findViewById(R.id.btSnooze);
        btSnooze.setOnClickListener(AlarmRingActivity.this);
        btCancel=(Button) confirm_dialog.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(AlarmRingActivity.this);

        confirm_dialog.show();


    }

    Thread thread=new Thread(new Runnable(){

        @Override
        public void run() {

                mp.start();

        }
    });

    void playTone(){

        thread.start();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btCancel:


                AlarmInfiList alarmInfiList=new AlarmInfiList();
                alarmInfiList= alarmInfiList.DeSerialize(this);
                if(alarmInfiList==null)
                    alarmInfiList=new AlarmInfiList();
                alarmInfiList.getAlamInfoList().remove(0);
                alarmInfiList.Serialize(this);
                confirm_dialog.setCancelable(false);
                confirm_dialog.dismiss();
                finish();
                break;
            case R.id.btSnooze:


                snooze();
                showNotification();
                confirm_dialog.dismiss();
                finish();
            break;
        }
    }

    private void showNotification() {
        NotificationManager alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent myIntent = new Intent(this, NotificationDismissedReceiver.class);
        myIntent.putExtra("Id",Id);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, Id, myIntent, 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("MY_ALARM").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("ALARM SNOOZED FOR 5 MIN"))
                .setContentText("ALARM SNOOZED FOR 5 MIN");


        alamNotificationBuilder.setDeleteIntent(contentIntent);
        alarmNotificationManager.notify(Id, alamNotificationBuilder.build());

    }

    private void snooze() {
           if(Id==-1)
               return;

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(this, AlarmReceiver.class);
        myIntent.putExtra("Id", Id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,Id, myIntent, 0);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis() + (5 * DateUtils.MINUTE_IN_MILLIS), pendingIntent);

    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(float force) {

        snooze();
        showNotification();
        confirm_dialog.dismiss();
        finish();

    }
}
