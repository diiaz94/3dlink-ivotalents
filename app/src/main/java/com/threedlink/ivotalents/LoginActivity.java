package com.threedlink.ivotalents;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.threedlink.ivotalents.DTO.Ticket;
import com.threedlink.ivotalents.DTO.User;
import com.threedlink.ivotalents.Services.ApiService;
import com.threedlink.ivotalents.Services.LoginService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, LoaderCallbacks<Cursor>, GoogleApiClient.OnConnectionFailedListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world", "diego@3dlink.com:ivotalent",
            "artist@artist.com:artist","industry@industry.com:industry","provider@provider.com:provider"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mEmailView;

    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private LoginButton btn_facebook_login;
    private CallbackManager callbackManager;
    public static int RC_FB_LOGIN_OK ;
    private SignInButton btn_google_login;
    //private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;

    private TextView name;

    private ImageButton btn_normal_login;


    // Session Manager Class
    SessionManager session;
    private IvoTalentsApp mApp;
    public static final int RC_GOOGLE_LOGIN_OK = 100;
    private ImageButton facebook_login;
    private ImageButton google_login;
    private TextView register_here;
    private TextView text_question;
    private TextView remember_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        facebook_login = (ImageButton) findViewById(R.id.facebookBtn);
        google_login = (ImageButton) findViewById(R.id.googleBtn);
        register_here = (TextView) findViewById(R.id.register_here);
        text_question = (TextView) findViewById(R.id.text_question);
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
        register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegisterScreen();
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
                                    finish();
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

        btn_google_login = (SignInButton) findViewById(R.id.btn_google_login);
        btn_google_login.setSize(SignInButton.SIZE_WIDE);
        btn_google_login.setScopes(googleSignInOptions.getScopeArray());
        btn_google_login.setOnClickListener(this);




        mEmailView = (EditText) findViewById(R.id.email);
        //populateAutoComplete();
        mEmailView.post(new Runnable() {
                      @Override
                      public void run() {
                          setHintTypeface(mEmailView, mApp.getFont());
                      }
                  }
        );
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

       // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        btn_normal_login = (ImageButton) findViewById(R.id.loginBtn);
        btn_normal_login.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        remember_password = (TextView) findViewById(R.id.remember_password);
        remember_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RememberPopUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
       });

        text_question.setTypeface(mApp.getFont());
        register_here.setTypeface(mApp.getFont());
        remember_password.setTypeface(mApp.getFont());
        mEmailView.setTypeface(mApp.getFont());
        mPasswordView.setTypeface(mApp.getFont());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.e("requestCode::",String.valueOf(requestCode));
        if(requestCode == RC_GOOGLE_LOGIN_OK ){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                session.createLoginSession(account.getDisplayName(), account.getEmail(),"GOOGLE");
                // Staring MainActivity
                goMainScreen();
                finish();
                Log.e("NAME::", account.getDisplayName());
            }
        }
        if(requestCode == RC_FB_LOGIN_OK){
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),R.string.error_login,Toast.LENGTH_SHORT).show();
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void goRegisterScreen() {
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void setHintTypeface(EditText editText, Typeface typeFace) {
        try {
            int hintColor = editText.getHintTextColors().getDefaultColor();

            Field hintLayoutField = TextView.class.getDeclaredField("mHintLayout");
            hintLayoutField.setAccessible(true);
            Layout hintLayout = (Layout) hintLayoutField.get(editText);

            TextPaint hintPaint = new TextPaint(hintLayout.getPaint());
            hintPaint.setTypeface(typeFace);
            hintPaint.setColor(hintColor);

            Field paintField = Layout.class.getDeclaredField("mPaint");
            paintField.setAccessible(true);
            paintField.set(hintLayout, hintPaint);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        ArrayList<String> errors= new ArrayList<String>();
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            //mEmailView.setError(getString(R.string.error_field_required));
            //focusView = mEmailView;
            errors.add(getResources().getString(R.string.error_email_required));
            cancel = true;
        } else if (!isEmailValid(email)) {
            //mEmailView.setError(getString(R.string.error_invalid_email));
            //focusView = mEmailView;
            errors.add(getResources().getString(R.string.error_invalid_email));
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
//            mPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mPasswordView;
            errors.add(getResources().getString(R.string.error_password_required));
            cancel = true;
        }else if (!isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
            errors.add(getResources().getString(R.string.error_invalid_password));
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
            mApp.showError(LoginActivity.this,errors);
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        //mEmailView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_google_login:
                signInWithGoogle();
                break;
            case R.id.loginBtn:
                attemptLogin();
                break;
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApp.getGoogleApiClient());
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN_OK);
    }






    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);



                User user = new User();
                user.setEmail("arte@arte.com");
                user.setPassword("p4ssw0rd");
                Call<Ticket> call = mApp.getApiServiceIntance().login(user);
                call.enqueue(new Callback<Ticket>() {
                    @Override
                    public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                        int statusCode = response.code();
                        Toast.makeText(getApplicationContext(),String.valueOf(statusCode),Toast.LENGTH_SHORT).show();
                        Ticket ticket = response.body();
                        Call<List<User>> callTalents = mApp.getApiServiceIntance().talents(ticket.getToken());

                        Response<List<User>> responseList = null;
                        try {
                            responseList = callTalents.execute();
                            List<User> talents = responseList.body();
                            Toast.makeText(getApplicationContext(),talents.get(0).getEmail()+talents.get(1).getEmail(),Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<Ticket> call, Throwable t) {

                    }
                });
            } catch (InterruptedException e) {
                return false;
            }


           /* for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }
                */
            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            ArrayList<String> errors= new ArrayList<String>();
            if (success) {
                session.createLoginSession(mEmail.split("@")[0], mEmail,"NORMAL");
                // Staring MainActivity
                goMainScreen();
                finish();
            } else {
                //Poner en popup
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
                errors.add(getString(R.string.error_incorrect_password));
                mApp.showError(LoginActivity.this,errors);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

