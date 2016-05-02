package com.one.assignment;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Sunny on 02-05-2016.
 */
public class LabApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
