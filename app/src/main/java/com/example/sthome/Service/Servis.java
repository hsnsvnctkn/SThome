package com.example.sthome.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sthome.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Servis extends Service {
    TextView lblGaz;
    String gaz;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lblGaz= (TextView) lblGaz.findViewById(R.id.lblGaz);
        new gazCek().execute();

        if(Integer.valueOf(gaz)>200){
            NotificationManager notif =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify= new Notification.Builder(getApplicationContext()).setContentTitle("Alarm !!!").setContentText("Dikkat Gaz Kaçağı Olabilir !!!").setSmallIcon(R.drawable.ic_notification).build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0,notify);
        }

    }
    public class gazCek extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc3 = Jsoup.connect("https://thingspeak.com/channels/763927/field/3/last.html").get();
                gaz = doc3.text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lblGaz.setText(gaz);
        }

    }
}

