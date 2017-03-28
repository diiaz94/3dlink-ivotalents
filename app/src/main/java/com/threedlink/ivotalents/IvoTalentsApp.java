package com.threedlink.ivotalents;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.threedlink.ivotalents.Services.ApiService;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private ApiService apiService;
    private FragmentTask mFragmentTask;

    private View mMainContainerView;
    private View mProgressView;
    private DrawerLayout mDrawer;
    public Activity mMainActivity;
    public FragmentManager mFragmenManager;


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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService =   retrofit.create(ApiService.class);

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
    public void logout(View view){
      SessionManager session = new SessionManager(getApplicationContext());
        // Clear the session data
        // This will clear all session data and
        // redirect user to LoginActivity
        boolean isLoggedWithFacebook = session.isLoggedWithFacebook();
        boolean isLoggedWithGoogle = session.isLoggedWithGoogle();
        session.logoutUser();
        if (isLoggedWithFacebook){
            //Log.d(TAG,"Loggeado con facebook");
            LoginManager.getInstance().logOut();
        }
        if (isLoggedWithGoogle){
           // Log.d(TAG,"Loggeado con google");
            Auth.GoogleSignInApi.signOut(this.getGoogleApiClient()).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                        }
                    });
        }
    }
    public int getResourcebyname(String resourceName){
        return getResources().getIdentifier(resourceName, "id", getPackageName());

    }

    public void setFontsOnRelative(RelativeLayout layout) {
        for( int i = 0; i < layout.getChildCount(); i++ ) {
            if (layout.getChildAt(i) instanceof TextView){
                TextView textView= (TextView) layout.getChildAt(i);
                if(textView.getTag()!=null){
                    textView.setTypeface(getFontApply(textView.getTag().toString()));
                }else{
                    textView.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof EditText){
                EditText editText= (EditText) layout.getChildAt(i);
                if(editText.getTag()!=null){
                    editText.setTypeface(getFontApply(editText.getTag().toString()));
                }else{
                    editText.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof CheckBox){
                CheckBox checkText= (CheckBox) layout.getChildAt(i);
                if(checkText.getTag()!=null){
                    checkText.setTypeface(getFontApply(checkText.getTag().toString()));
                }else{
                    checkText.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear= (LinearLayout) layout.getChildAt(i);
                setFontsOnLinear(linear);
            }else if(layout.getChildAt(i) instanceof RelativeLayout){
                RelativeLayout relative= (RelativeLayout) layout.getChildAt(i);
                setFontsOnRelative(relative);
            }else if(layout.getChildAt(i) instanceof ScrollView){
                ScrollView scroll= (ScrollView) layout.getChildAt(i);
                setFontsOnScroll(scroll);
            }
        }

    }
    public void setFontsOnLinear(LinearLayout layout) {
        for( int i = 0; i < layout.getChildCount(); i++ ) {
            if (layout.getChildAt(i) instanceof TextView){
                TextView textView= (TextView) layout.getChildAt(i);
                if(textView.getTag()!=null){
                    textView.setTypeface(getFontApply(textView.getTag().toString()));
                }else{
                    textView.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof EditText){
                EditText editText= (EditText) layout.getChildAt(i);
                if(editText.getTag()!=null){
                    editText.setTypeface(getFontApply(editText.getTag().toString()));
                }else{
                    editText.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear= (LinearLayout) layout.getChildAt(i);
                setFontsOnLinear(linear);
            }else if(layout.getChildAt(i) instanceof RelativeLayout){
                RelativeLayout relative= (RelativeLayout) layout.getChildAt(i);
                setFontsOnRelative(relative);
            }else if(layout.getChildAt(i) instanceof ScrollView){
                ScrollView scroll= (ScrollView) layout.getChildAt(i);
                setFontsOnScroll(scroll);
            }
        }
    }
    public void setFontsOnScroll(ScrollView layout) {
        for( int i = 0; i < layout.getChildCount(); i++ ) {
            if (layout.getChildAt(i) instanceof TextView){
                TextView textView= (TextView) layout.getChildAt(i);
                if(textView.getTag()!=null){
                    textView.setTypeface(getFontApply(textView.getTag().toString()));
                }else{
                    textView.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof EditText){
                EditText editText= (EditText) layout.getChildAt(i);
                if(editText.getTag()!=null){
                    editText.setTypeface(getFontApply(editText.getTag().toString()));
                }else{
                    editText.setTypeface(getFont());
                }
            }else if(layout.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear= (LinearLayout) layout.getChildAt(i);
                setFontsOnLinear(linear);
            }else if(layout.getChildAt(i) instanceof RelativeLayout){
                RelativeLayout relative= (RelativeLayout) layout.getChildAt(i);
                setFontsOnRelative(relative);
            }else if(layout.getChildAt(i) instanceof ScrollView){
                ScrollView scroll= (ScrollView) layout.getChildAt(i);
                setFontsOnScroll(scroll);
            }
        }

    }

    public Typeface getFontApply(String tag){
        if(tag.indexOf("normal")!=-1){
            return getFont();
        }
        if(tag.indexOf("semibolditalic")!=-1) {
            return getFontSemiBoldItalic();
        }
        if(tag.indexOf("semibold")!=-1){
            return getFontSemiBold();
        }
        if(tag.indexOf("bold")!=-1){
            return getFontBold();
        }
        if(tag.indexOf("light")!=-1) {
            return getFontLight();
        }

        return getFont();

    }

    public ApiService getApiServiceIntance() {
        return apiService;
    }

    public void setAsyncElementsUI(Activity mMainActivity,FragmentManager mFragmenManager,View mProgressView,View mMainContainerView,DrawerLayout mDrawer){
        this.mMainActivity = mMainActivity;
        this.mFragmenManager = mFragmenManager;
        this.mProgressView = mProgressView;
        this.mMainContainerView = mMainContainerView;
        this.mDrawer = mDrawer;
    }

    public void loadFragment(Fragment fragment){
        mFragmentTask = new FragmentTask(fragment);
        mFragmentTask.execute((Void) null);
    }

    /**
     * Shows the progress UI and hides the container main.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMainContainerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMainContainerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMainContainerView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mMainContainerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class FragmentTask extends AsyncTask<Void, Void, Boolean> {

        private Fragment mFragment;


        FragmentTask(Fragment fragment) {
            mFragment = fragment;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(mFragment!=null){
                    mMainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                            showProgress(true);
                        }
                    });

                    mFragmenManager
                            .beginTransaction()
                            .replace(R.id.content_main, mFragment)
                            .addToBackStack(mFragment.getClass().getSimpleName())
                            .commit();
                    return true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mFragmentTask = null;
            showProgress(false);
            if (success) {

            } else {

            }

            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            }
        }

        @Override
        protected void onCancelled() {
            mFragmentTask = null;
            showProgress(false);
        }
    }
}
