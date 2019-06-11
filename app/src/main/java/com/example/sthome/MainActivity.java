package com.example.sthome;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sthome.Service.Servis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    TextView lblGaz;
    TextView lblSicaklik;
    TextView lblTnem;
    TextView lblhirsizDurum;

    String gaz,sicaklik,toprakNem,hirsizDurum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(),Servis.class);
        startService(intent);

        Button btnKontrol = (Button) this.findViewById(R.id.btnKontrol);
        final Intent newWindow = new Intent(getBaseContext(),Kontrol.class);
        lblGaz= (TextView) this.findViewById(R.id.lblGaz);
        lblSicaklik=(TextView) this.findViewById(R.id.lblSicaklik);
        lblTnem=(TextView) this.findViewById(R.id.lblTnem);
        lblhirsizDurum=(TextView) this.findViewById(R.id.lblhirsizDurum);
        Button btnUpdate = (Button) this.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new pullData().execute();
            }
        });

        btnKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newWindow);
            }
        });
    }
    public class pullData extends AsyncTask<Void,Void,Void>{  //Linkteki verileri çekip değişkene atma

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://thingspeak.com/channels/763927/field/1/last.html").get();
                Document doc2 = Jsoup.connect("https://thingspeak.com/channels/763927/field/2/last.html").get();
                Document doc3 = Jsoup.connect("https://thingspeak.com/channels/763927/field/3/last.html").get();
                Document doc4 = Jsoup.connect("https://thingspeak.com/channels/763927/field/4/last.html").get();
                gaz = doc3.text();//Document doc =Jsoup.connect("https://thingspeak.com/channels/763927/field/1/last.html").get();
                sicaklik = doc.text();
                toprakNem = doc2.text();
                hirsizDurum = doc4.text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lblSicaklik.setText(sicaklik);
            lblGaz.setText(gaz);
            lblTnem.setText(toprakNem);
            if(hirsizDurum == "1") {
                lblhirsizDurum.setText("Tehlike!");
            }
            else {
                lblhirsizDurum.setText("Tehlike Yok");
            }

        }
    }
}

