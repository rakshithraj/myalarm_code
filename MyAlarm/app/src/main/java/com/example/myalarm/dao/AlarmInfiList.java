package com.example.myalarm.dao;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rakshith on 7/13/2015.
 */
public class AlarmInfiList implements Serializable {
    public ArrayList<PendingIntent> getIntentArray() {
        return intentArray;
    }

    public void setIntentArray(ArrayList<PendingIntent> intentArray) {
        this.intentArray = intentArray;
    }

    ArrayList<PendingIntent> intentArray=new ArrayList<PendingIntent>();
    ArrayList<AlamInfo> AlamInfoList=new  ArrayList<AlamInfo>();

    public ArrayList<AlamInfo> getAlamInfoList() {
        return AlamInfoList;
    }

    public void setAlamInfoList(ArrayList<AlamInfo> alamInfoList) {
        AlamInfoList = alamInfoList;
    }

    public void Serialize(Context context) {
        try {
            ContextWrapper c = new ContextWrapper(context);
            String Path;
            Path = c.getFilesDir().getAbsolutePath() + "/eventtypeinfo.bin";

            //ObjectOutputStream oos = new ObjectOutputStream(
            //        new FileOutputStream(new File(Path)));
            FileOutputStream outputStream = context.openFileOutput("AlarmInfiList.bin",context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(
                    outputStream);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            Log.d("tag", "File Serialized Successfully");
        } catch (Exception ex) {
            Log.d("tag", "File Serialized not Success= " + ex.toString());
            Log.v("Address Book", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public AlarmInfiList DeSerialize(Context context) {
        ContextWrapper c = new ContextWrapper(context);
        String Path = c.getFilesDir().getAbsolutePath() + "/StickyInfoList.bin";

        File file = new File(Path);
        try {
            FileInputStream inputStream = context.openFileInput("AlarmInfiList.bin");
//         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
//                 file));
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Object obj = ois.readObject();
            ois.close();
            Log.d("tag", "File DeSerialized Successfully");
            return (AlarmInfiList) obj;
        } catch (Exception ex) {
            Log.d("tag", "File derialized not Success= " + ex.toString());
            Log.v("Address Book", ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
