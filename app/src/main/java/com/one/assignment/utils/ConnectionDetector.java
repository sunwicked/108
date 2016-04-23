package com.one.assignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//Detecting if user has active internet connection
public class ConnectionDetector {


    public static boolean isConnectingToInternet(Context _context) {

        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //checking for internet connection
        if (connectivity != null) {
            //fetching all possible networks
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }
}