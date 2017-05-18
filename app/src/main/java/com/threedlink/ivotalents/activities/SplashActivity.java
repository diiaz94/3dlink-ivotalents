package com.threedlink.ivotalents.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.fragments.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = SplashActivity.class.getSimpleName();
    // Session Manager Class
    SessionManager session;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private IvoTalentsApp mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Session class instance
        session = new SessionManager(getApplicationContext());
        mApp = ((IvoTalentsApp) getApplicationContext());
        mApp.setGoogleApiClient(new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mApp.getGoogleSignInOptions())
                .build());
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

            }
        }, SPLASH_DISPLAY_LENGTH);*/
    }
    @Override
    public void onResume() {
        Log.e("PEDRO","PASO onStart MainAct");
        super.onResume();

        if(session.checkLogin()){
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            goToMainScreen();
                        }
                    },
                    3000);

        }else{
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mApp.getGoogleApiClient());
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(TAG, "Got cached sign-in");
                GoogleSignInAccount acct = opr.get().getSignInAccount();
                session.createLoginSession(acct.getDisplayName(),acct.getEmail(),"GOOGLE", "");

            }else
            if(AccessToken.getCurrentAccessToken() != null) {
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v(TAG, response.toString());
                                // Application code
                                try {
                                    Log.e("FACEBOOK","Try access fb data::+"+object.toString());
                                    session.createLoginSession(object.getString("name"),  object.getString("email"),"FACEBOOK", "");
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //finish();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }else{
                finish();
            }


        }


    }

    public void goToMainScreen(){
        /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | mainIntent.FLAG_ACTIVITY_CLEAR_TASK | mainIntent.FLAG_ACTIVITY_NEW_TASK);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    };
    public void goToLoginScreen(){
        /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | mainIntent.FLAG_ACTIVITY_CLEAR_TASK | mainIntent.FLAG_ACTIVITY_NEW_TASK);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    };
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
