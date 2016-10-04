package com.threedlink.ivotalents;

import android.app.Application;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by diiaz94 on 04-10-2016.
 */
public class IvoTalentsApp extends Application {

    @Override
    public void onCreate(){
        Log.e("PEDRO","PASO onStart PHOTOTESTER");
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


    }
}
