package com.yuvarani.interview_task.assessmenttask;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.yuvarani.interview_task.R;
import com.yuvarani.interview_task.WifiReceiver;


public class DistancecalculationActivity extends AppCompatActivity {
    String TAG = "DistanceCalculatormainActivity";
    private WifiManager wifiManager;
    TextView distanceTV;
    String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.CHANGE_WIFI_STATE,

    };
    private static final int PERMISSION_REQUEST = 2;

    Activity context;
Button task2btn;
    TextView tiltle_toolbar;
    LinearLayout ll_bck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_calculation);
       // checkpermisison();
        tiltle_toolbar=findViewById(R.id.tv_title);
        tiltle_toolbar.setText("Distance Calculation");
        ll_bck=findViewById(R.id.ll_bck);
        ll_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // Calculate the distance to the destination device.
        int distance = calculateDistance();
        // Update the distance displayed on the screen.
        distanceTV = (TextView) findViewById(R.id.distanceTV);
        task2btn=findViewById(R.id.task2btn);
        new WifiReceiver().settextview(distanceTV); // received value from broadcast receivere
        distanceTV.setText("The distance to the destination device is " + distance + " mm.");


    }

    private void checkpermisison() {

        if (!hasPermissions(context.getApplicationContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(DistancecalculationActivity.this, PERMISSIONS, PERMISSION_REQUEST);
            // ActivityCompat.requestPermissions(LoginPage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            // Permission is granted

        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getDestinationMacAddress() {
        // Get the current Wi-Fi network.
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        // Get the destination MAC address.
        String destinationMacAddress = wifiInfo.getBSSID();

        Log.d(TAG, "The destination MAC address is " + destinationMacAddress);

        return destinationMacAddress;
    }

    public int calculateDistance() {
        // Get the signal strength of the signal from the destination device.
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        int signalStrength = wifiManager.calculateSignalLevel(
                wifiInfo.getRssi(),
                5
        );

        // Calculate the distance using the Friis transmission equation.
        double distance = Math.pow(10.0, (-108.14 - (20.0 * Math.log10(signalStrength)) -
                (20.0 * Math.log10(900000.0)))) * 1000.0;

        Log.d(TAG, "The distance to the destination device is " + distance + " mm.");

        return (int) distance;
    }
}
