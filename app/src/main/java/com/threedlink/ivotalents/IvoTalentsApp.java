package com.threedlink.ivotalents;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 04-10-2016.
 */
public class IvoTalentsApp extends Application {


    private Typeface face;
    private Typeface faceBold;
    private Typeface faceSemiBold;
    private Typeface faceSemiBoldItalic;
    private Typeface faceLight;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    @Override
    public void onCreate(){
        Log.e("PEDRO","PASO onStart IVOTALENTS");
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        face=Typeface.createFromAsset(getAssets(),"fonts/Exo2-Regular.otf");
        faceBold=Typeface.createFromAsset(getAssets(),"fonts/Exo2-Bold.otf");
        faceSemiBold=Typeface.createFromAsset(getAssets(),"fonts/Exo2-SemiBold.otf");
        faceSemiBoldItalic=Typeface.createFromAsset(getAssets(),"fonts/Exo2-SemiBoldItalic.otf");
        faceLight=Typeface.createFromAsset(getAssets(),"fonts/Exo2-Light.otf");
    }


    public Typeface getFont(){
        return this.face;
    }
    public Typeface getFontBold(){
        return this.faceBold;
    }
    public Typeface getFontSemiBold(){
        return this.faceSemiBold;
    }
    public Typeface getFontSemiBoldItalic(){
        return this.faceSemiBoldItalic;
    }
    public Typeface getFontLight(){
        return this.faceLight;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        return googleSignInOptions;
    }

    public void setGoogleSignInOptions(GoogleSignInOptions googleSignInOptions) {
        this.googleSignInOptions = googleSignInOptions;
    }

    public void showError(Activity activity, ArrayList<String> errors){
        Intent intent = new Intent(activity,PopupInfo.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        PopupInfo.lstError = errors;
        startActivity(intent);
    }
}
