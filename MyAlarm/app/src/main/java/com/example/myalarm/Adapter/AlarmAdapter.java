package com.example.myalarm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myalarm.MainActivity;
import com.example.myalarm.R;
import com.example.myalarm.dao.AlamInfo;

import java.util.ArrayList;

/**
 * Created by Rakshith on 7/13/2015.
 */
public class AlarmAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private MainActivity activity;
    private ArrayList<AlamInfo> data;
    private static LayoutInflater inflater=null;

    public static final int itemId = 1234;
    public AlarmAdapter(MainActivity activity, ArrayList<AlamInfo> data) {


        this.activity = activity;
        this.data=data;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
     TextView tvDate,tvTime;




    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;
        AlamInfo emergecyContactInfo;

        if(convertView==null){

            vi = inflater.inflate(R.layout.custom_alarm_layout, null);
            holder=new ViewHolder();
            holder.tvDate=(TextView)vi.findViewById(R.id.tvDate);
            holder.tvTime=(TextView)vi.findViewById(R.id.tvTime);







            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );

        }
        else
            holder=(ViewHolder)vi.getTag();



        if(data.size()<=0)
        {
            vi.setVisibility(View.GONE);

        }
        else
        {
            vi.setVisibility(View.VISIBLE);
            emergecyContactInfo=data.get(position);

            holder.tvDate.setText(emergecyContactInfo.getDate());
            holder.tvTime.setText(emergecyContactInfo.getTime());
        }





        return vi;
    }



    private class onCallClick implements View.OnClickListener {



        public onCallClick() {
            // TODO Auto-generated constructor stub


        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub




        }

    }


}