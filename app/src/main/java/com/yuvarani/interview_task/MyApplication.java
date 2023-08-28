package com.yuvarani.interview_task;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this);

    }
}

