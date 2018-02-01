package com.example.andilaptop.hellobeacon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndiLaptop on 11.01.2018.
 */

public class AddBeaconsActivity  extends AppCompatActivity {

    ArrayList<Beacon> beaconList = new ArrayList<>();
    ArrayList<String> beaconStringList = new ArrayList<>();


    TextView beaconView;
    LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbeacons);


        beaconList = (ArrayList<Beacon>)getIntent().getExtras().get("beaconList");
        linearLayout =  findViewById(R.id.linearLayoutAdd);

        for (int i=0; i<beaconList.size(); i++) {
            Beacon b = beaconList.get(i);
            beaconStringList.add("beaconNr "+i+": " +b.getUUID() + "\n " + b.getMajor()+" "+ b.getMinor() + "\n");
            beaconView = new TextView(this);
            beaconView.setText(beaconStringList.get(i));
            linearLayout.addView(beaconView);
        }

    }


    public void onAddBeaconsClick(View v) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivityIntent.putExtra("beaconList", beaconList);
        startActivity(mainActivityIntent);
    }


}
