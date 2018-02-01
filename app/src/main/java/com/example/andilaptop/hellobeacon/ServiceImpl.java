package com.example.andilaptop.hellobeacon;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServiceImpl extends IntentService {

    private long seconds;
    //TODO: include needed fields
    File beaconsFile;
    Intent intent;

    FileInputStream is;
    BufferedReader reader;
    ArrayList<Beacon> beaconList = new ArrayList<Beacon>();

    public ServiceImpl() {
        super("IntentService");
    }

   // public int onStartCommand(Intent intent, int flags, int startId) {

    //   onHandleIntent(intent);

      //  return Service.START_STICKY;
    //}
    protected void onHandleIntent(Intent intent) {

        this.intent = intent;

        //TODO: uncomment this, when implemented setupInputReader();
        setupInputReader();

        //TODO: get the seconds from intent
        seconds = intent.getExtras().getInt("TIME_SECONDS");

        //Toast.makeText(this.getApplicationContext(), "Ende setupinputreader()", Toast.LENGTH_LONG).show();

        int i= 0;
        //how long the service should sleep, in milliseconds
        long millis = seconds * 1000;
        while (true) {

           // synchronized (this) {


                try {
                    Beacon beacon = scanBeacon();

                    if (beacon != null) {
                        //TODO: add beaconList to the List of scanned beaconList
                        beaconList.add(beacon);
                        //Log.println(Log.DEBUG, "beacon", "becaon hinzugefügt *******++***+");

                        //TODO: notification
                        Notification.Builder notificationBuilder = new Notification.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
                                .setContentTitle("New Beacon found!") // title for notification
                                .setContentText("New Beacon found: major: " + beacon.getMajor() +
                                        ", Minor: " + beacon.getMinor() + ".") // message for notification
                                .setAutoCancel(true); // clear notification after click

                        //TODO: intent to AddBeaconsActivity
                        Intent addBeaconsActivityIntent = new Intent(this, AddBeaconsActivity.class);
                        addBeaconsActivityIntent.putExtra("beaconList", beaconList);


                        //build intent to switch to activity on click
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

                        //adds the back stack for the Intent (not the intent itself)
                        stackBuilder.addParentStack(AddBeaconsActivity.class);

                        //adds the intent that starts and puts the activity to the top of the stack
                        //TODO: uncomment this and insert the above created intent as input
                        stackBuilder.addNextIntent(addBeaconsActivityIntent);

                        //PendingIntent waits for an event
                        PendingIntent scanResultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                        // "original content"
                        notificationBuilder.setContentIntent(scanResultPendingIntent);
                        Notification notification = notificationBuilder.build();
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notification);

                    }

                    //TODO: put the service to sleep

                    //wait(millis);
                    Thread.sleep(millis);
                    //stopSelf();
                    //Thread.currentThread().interrupt();


                }catch (InterruptedException iEx) {

                    Log.d("Message", iEx.getMessage());
                }

                i++;
            //}
        }
    }

    private void setupInputReader() {

        //TODO: read the file "Beacon.txt"
        //read the header in advance to exclude it from the output

        beaconsFile = (File) intent.getExtras().get("Beacons.txt");

        //Log.println(Log.DEBUG, "beacon", "setupInputReader beginnt");
        //Log.println(Log.DEBUG, "beacon", "setupInputReader beginnt");

        String line = "default";

        try {
            if (beaconsFile.exists()) {
                is = new FileInputStream(beaconsFile);
                reader = new BufferedReader(new InputStreamReader(is));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this.getApplicationContext(), "File not found"+line, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this.getApplicationContext(), "IOExpection in setup file"+line, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //Toast.makeText(this.getApplicationContext(), "Ende setupinputreader()"+line, Toast.LENGTH_LONG).show();


    }

    private Beacon scanBeacon() {
        //TODO: Read a line and split one row into the beacon components uuid, major and minor
        //create a new beacon and return it

        String[] lineA = new String[3];
        String uuid = "";
        int major = 0;
        int minor = 0;

        //Toast.makeText(this.getApplicationContext(), "Lesen von Beacons startet", Toast.LENGTH_LONG).show();
        Log.println(Log.DEBUG, "beacon", "scanBeacon beginnt");



        try {



            lineA = reader.readLine().split(",");
            String[] beaconData = lineA;

            uuid = beaconData[0];
            major = Integer.parseInt(beaconData[1]);
            minor = Integer.parseInt(beaconData[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Toast.makeText(this.getApplicationContext(), "scanBeacon()"+lineA[1]+lineA[2], Toast.LENGTH_LONG).show();
        return new Beacon(uuid, major, minor);
    }


    public void onDestroy() {
        //TODO: implement this
        Toast.makeText(this.getApplicationContext(), "in onDestroy", Toast.LENGTH_LONG).show();
        //super.onDestroy();
        //stopSelf();
    }
}