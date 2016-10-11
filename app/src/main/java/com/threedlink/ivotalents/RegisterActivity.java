package com.threedlink.ivotalents;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageButton register_button;
    private FrameLayout register_step_1;
    private ImageButton artist_button;
    private ImageButton industry_button;
    private ImageButton provider_button;
    private ImageButton fan_button;
    private FrameLayout register_step_2;

    private EditText user;
    private EditText email;
    private EditText pass;
    private EditText pass_confirm;

    private LoginButton btn_facebook_login;
    private CallbackManager callbackManager;
    public static int RC_FB_LOGIN_OK ;

    private SignInButton btn_google_login;
    private GoogleSignInOptions googleSignInOptions;
    SessionManager session;
    private IvoTalentsApp mApp;
    public static final int RC_GOOGLE_LOGIN_OK = 100;
    private ImageButton facebook_login;
    private ImageButton google_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_button = (ImageButton) findViewById(R.id.register_btn);
        register_step_1 = (FrameLayout) findViewById(R.id.register_step_1);
        register_step_2 = (FrameLayout) findViewById(R.id.register_step_2);
        artist_button = (ImageButton) findViewById(R.id.artist_btn);
        industry_button = (ImageButton) findViewById(R.id.industry_btn);
        provider_button = (ImageButton) findViewById(R.id.provider_btn);
        //fan_button = (ImageButton) findViewById(R.id.fan_btn);


        user = (EditText) findViewById(R.id.user);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        pass_confirm = (EditText) findViewById(R.id.pass_confirm);



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allValids = true;
                String user_text = user.getText().toString();
                String email_text = email.getText().toString();
                String pass_text = pass.getText().toString();
                String pass_confirm_text = pass_confirm.getText().toString();
                user.setError(null);
                email.setError(null);
                pass.setError(null);
                pass_confirm.setError(null);

                View focusView = null;





                if (TextUtils.isEmpty(pass_confirm_text)){
                    pass_confirm.setError(getString(R.string.error_field_required));
                    allValids=false;
                    focusView=pass_confirm;
                }
                if (TextUtils.isEmpty(pass_text)){
                    pass.setError(getString(R.string.error_field_required));
                    allValids=false;
                    focusView=pass;
                }

                if (!TextUtils.isEmpty(pass_confirm_text) && !TextUtils.isEmpty(pass_text)){
                    if (!pass_text.equalsIgnoreCase(pass_confirm_text)){
                        pass.setError(getString(R.string.error_passwords_not_equals));
                        allValids=false;
                        focusView=pass;
                    }
                }


                if (TextUtils.isEmpty(email_text)){
                    email.setError(getString(R.string.error_field_required));
                    allValids=false;
                    focusView=email;
                }else{
                    if(!email.getText().toString().contains("@")){
                        email.setError(getString(R.string.error_invalid_email));
                        allValids=false;
                        focusView=email;
                    }
                }

                if (TextUtils.isEmpty(user_text)){
                    user.setError(getString(R.string.error_field_required));
                    allValids=false;
                    focusView=user;
                }

                if(allValids){
                    register_step_1.setVisibility(View.INVISIBLE);
                    register_step_2.setVisibility(View.VISIBLE);
                }else{
                    focusView.requestFocus();

                }
            }
        });
        artist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMainScreen();
            }
        });
        industry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMainScreen();
            }
        });
        provider_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMainScreen();
            }
        });
        /*fan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMainScreen();
            }
        });*/



        facebook_login = (ImageButton) findViewById(R.id.facebookBtn);
        google_login = (ImageButton) findViewById(R.id.googleBtn);

        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_facebook_login.performClick();
            }
        });
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        // Session Manager
        session = new SessionManager(getApplicationContext());
        // Set up the login form.
        mApp = ((IvoTalentsApp) getApplicationContext());
        //mApp = new PhotoTesterApp(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        btn_facebook_login = (LoginButton) findViewById(R.id.btn_facebook_login);
        btn_facebook_login.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        btn_facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                // Application code
                                try {
                                    Log.e("FACEBOOK","Try access fb data::+"+object.toString());
                                    session.createLoginSession(object.getString("name"),  object.getString("email"),"FACEBOOK");
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                    goMainScreen();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                finish();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.cancel_login,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.error_login,Toast.LENGTH_SHORT).show();
            }
        });
        RC_FB_LOGIN_OK=btn_facebook_login.getRequestCode();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mApp.setGoogleApiClient( new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApp.getGoogleApiClient());
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN_OK);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),R.string.error_register,Toast.LENGTH_SHORT).show();
    }
}
