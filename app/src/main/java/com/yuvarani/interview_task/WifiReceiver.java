package com.yuvarani.interview_task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

public class WifiReceiver extends BroadcastReceiver {
    private static TextView distanceTV;
    public void settextview(TextView textView){
        WifiReceiver.distanceTV=textView;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {
                // Do your work.
                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //String ssid = wifiInfo.getSSID();
                int signalStrength = wifiManager.calculateSignalLevel(
                        wifiInfo.getRssi(),
                        5
                );

                // Calculate the distance using the Friis transmission equation.
                double distance = Math.pow(10.0, (-108.14 - (20.0 * Math.log10(signalStrength)) -
                        (20.0 * Math.log10(900000.0)))) * 1000.0;
             // System.out("",distance);
                Log.d("distance+++",""+distance);
                distanceTV.setText("The distance to the destination device is " + distance + " mm.");

            }
        }
    }

}
