package com.example.myalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.example.myalarm.Adapter.AlarmAdapter;
import com.example.myalarm.BroadcastReceiver.AlarmReceiver;
import com.example.myalarm.Ultility.SwipeDismissListViewTouchListener;
import com.example.myalarm.dao.AlamInfo;
import com.example.myalarm.dao.AlarmInfiList;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    Button btAddAlarm;
    ListView listAlarm;
    AlarmAdapter alarmAdapter;
    ArrayList<AlamInfo> data=new ArrayList<AlamInfo> ();
    AlarmInfiList alarmInfiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        intialize();

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listAlarm,
                        new SwipeDismissListViewTouchListener.OnDismissCallback() {

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    data.remove(position);
                                    alarmInfiList=new AlarmInfiList();
                                    alarmInfiList= alarmInfiList.DeSerialize(MainActivity.this);
                                    if(alarmInfiList==null)
                                        alarmInfiList=new AlarmInfiList();

                                    AlarmManager alarmManager = (AlarmManager)MainActivity.this.getSystemService(ALARM_SERVICE);
                                    Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                                    int alarmId=alarmInfiList.getAlamInfoList().get(position).getId();
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,alarmId, myIntent, 0);
                                    alarmManager.cancel(pendingIntent);

                                    alarmInfiList.getAlamInfoList().remove(position);
                                    alarmInfiList.Serialize(MainActivity.this);
                                }
                                alarmAdapter.notifyDataSetChanged();


                            }
                        });

        listAlarm.setOnTouchListener(touchListener);
        listAlarm.setOnScrollListener(touchListener.makeScrollListener());
    }


    private void intialize() {
        listAlarm=(ListView)this.findViewById(R.id.listAlarm);
        alarmAdapter=new AlarmAdapter(this,data);
        btAddAlarm=(Button)this.findViewById(R.id.btAddAlarm);
        btAddAlarm.setOnClickListener(this);
        listAlarm.setAdapter(alarmAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmInfiList=new AlarmInfiList();
        alarmInfiList= alarmInfiList.DeSerialize(this);
        if(alarmInfiList==null)
            alarmInfiList=new AlarmInfiList();
        data.clear();
        data.addAll(alarmInfiList.getAlamInfoList());
        alarmAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btAddAlarm:
                    Intent intent=new Intent(this,AddAlarmActivity.class);
                    this.startActivity(intent);
                break;
        }
    }


}
