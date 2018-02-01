package com.example.andilaptop.hellobeacon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


        //Delimiter used in file
    public static final String COMMA_DELIMITER = ",";

    //new line
    private static final String NEW_LINE_SEPARATOR = "\n";

    //file header
    private static final String FILE_HEADER = "UUID,MAJOR,MINOR";;

    //TODO: add needed fields

    Intent serviceImplIntent;
    ArrayList<Beacon> beaconList = new ArrayList<>();

    ArrayList<String> beaconStringList = new ArrayList<>();

    TextView beaconView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout =  findViewById(R.id.linearLayout);

            beaconStringList.add("beaconNr | UUID | major | minor \n");
            beaconView = new TextView(this);
            beaconView.setText(beaconStringList.get(0));
            linearLayout.addView(beaconView);


        Button startButton = findViewById(R.id.startScan);
        Button stopButton = findViewById(R.id.stopScan);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startServiceByButtonClick((Button) view);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopServiceByButtonClick((Button) view);
            }
        });
        //TODO: get intent
        serviceImplIntent = getIntent();


    }

    @Override
    protected void onResume() {
        //TODO: get intent and add beaconList to List
        super.onResume();

        serviceImplIntent = getIntent();
        showBeaconsInLinearLayout();

    }

    private void showBeaconsInLinearLayout() {
        //TODO: implement this
        //linearLayout.addView(new TextView(this.getApplicationContext()));


        try {
            if (getIntent().getExtras().get("beaconList") != null) {
                beaconList = (ArrayList<Beacon>) getIntent().getExtras().get("beaconList");
                linearLayout = findViewById(R.id.linearLayout);

                for (int i = 1; i < beaconList.size(); i++) {
                    Beacon b = beaconList.get(i);
                    beaconStringList.add("beaconNr " + i + ": " + b.getUUID() + "\n " + b.getMajor() + " " + b.getMinor() + "\n");
                    beaconView = new TextView(this);
                    beaconView.setText(beaconStringList.get(i));
                    linearLayout.addView(beaconView);
                }
            }
            else {
                linearLayout = findViewById(R.id.linearLayoutAdd);
                beaconView = new TextView(this);
                beaconView.setText("No beacons yet");
                linearLayout.addView(beaconView);
            }
        } catch (Exception e) {
            Log.println(Log.DEBUG, "beacon", "No beacons yet");;
        }
    }

    //Do not change this!
    protected void writeBeaconSimulationFile(){

        //Create new beacon objects  1) uuid 2) major=floor level 3) minor=room
        Beacon beacon1 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,1);
        Beacon beacon2 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,2);
        Beacon beacon3 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,3);
        Beacon beacon4 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,4);
        Beacon beacon5 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,5);
        Beacon beacon6 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,8);
        Beacon beacon7 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,9);
        Beacon beacon8 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,10);
        Beacon beacon9 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,10);
        Beacon beacon10 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,9);
        Beacon beacon11 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,8);
        Beacon beacon12 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,5);
        Beacon beacon13 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,4);
        Beacon beacon14 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,3);
        Beacon beacon15 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,2);
        Beacon beacon16 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,1);

        //Create a new list of beaconList objects
        ArrayList<Beacon> beacons = new ArrayList<Beacon>();
        beacons.add(beacon1);
        beacons.add(beacon2);
        beacons.add(beacon3);
        beacons.add(beacon4);
        beacons.add(beacon5);
        beacons.add(beacon6);
        beacons.add(beacon7);
        beacons.add(beacon8);
        beacons.add(beacon9);
        beacons.add(beacon10);
        beacons.add(beacon11);
        beacons.add(beacon12);
        beacons.add(beacon13);
        beacons.add(beacon14);
        beacons.add(beacon15);
        beacons.add(beacon16);
        beacons.add(beacon15);
        beacons.add(beacon14);
        beacons.add(beacon3);
        beacons.add(beacon2);
        beacons.add(beacon1);


        try{
            FileOutputStream testFile = openFileOutput("Beacons.txt", Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(testFile);
            outputStreamWriter.append(FILE_HEADER.toString());
            outputStreamWriter.append(NEW_LINE_SEPARATOR);

            for (Beacon beacon : beacons) {
                outputStreamWriter.append(String.valueOf(beacon.getUUID()));
                outputStreamWriter.append(COMMA_DELIMITER);
                outputStreamWriter.append(String.valueOf(beacon.getMajor()));
                outputStreamWriter.append(COMMA_DELIMITER);
                outputStreamWriter.append(String.valueOf(beacon.getMinor()));
                outputStreamWriter.append(NEW_LINE_SEPARATOR);
            }

            outputStreamWriter.close();
        }
        catch (IOException ex){
            Log.d("Message", ex.getMessage());
        }
    }

    //TODO: button starts the service
    public void startServiceByButtonClick(View v) {
        //TODO: Get user input
        TextView userInput = findViewById(R.id.enterSeconds);
        int seconds = Integer.parseInt(userInput.getText().toString());


        Toast.makeText(this.getApplicationContext(), "Start button pressed! Seconds: "+userInput.getText(),
                Toast.LENGTH_LONG).show();


        ServiceImpl serviceIntent = new ServiceImpl();
        Intent serviceImplIntent = new Intent(this, ServiceImpl.class);
        serviceImplIntent.putExtra("TIME_SECONDS", seconds);

        //Do not change this!
        File dir = getFilesDir();
        File file = new File(dir, "Beacons.txt");
        //boolean deleted = file.delete();

        //this method writes the file containing simulated beacon data
        writeBeaconSimulationFile();



        //TODO: Service is started via intent
        serviceImplIntent.putExtra("Beacons.txt", file);
        //serviceImplIntent.putExtra("TextView", R.id.textView);
        startService(serviceImplIntent);


        //Toast.makeText(this.getApplicationContext(), "Beacons exists? "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();

    }

    //TODO: stop service
    public void stopServiceByButtonClick(View v) {
        //implement this

        stopService(new Intent(this, ServiceImpl.class));
        //stopService(serviceImplIntent);
        // clean up
        beaconList = new ArrayList<>();

        beaconStringList = new ArrayList<>();

        //Toast.makeText(this.getApplicationContext(), "Stop button pressed!", Toast.LENGTH_LONG).show();
    }

}
