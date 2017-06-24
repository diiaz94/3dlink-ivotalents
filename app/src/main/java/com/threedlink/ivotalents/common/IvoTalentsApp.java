package com.threedlink.ivotalents.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.activities.PopupInfoActivity;
import com.threedlink.ivotalents.fragments.SessionManager;
import com.threedlink.ivotalents.common.services.IvoService;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diiaz94 on 04-10-2016.
 */
public class IvoTalentsApp extends Application {



    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    private IvoService ivoService;
    private FragmentTask mFragmentTask;
    private FontApplyTask mFontApplyTask;

    private View mMainContainerView;
    private View mProgressView;
    private DrawerLayout mDrawer;
    public Activity mMainActivity;
    public FragmentManager mFragmenManager;
    public ImageLoader imageLoader;
    private RolEntity entitySession;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    @Override
    public void onCreate(){
        Log.e("PEDRO","PASO onStart IVOTALENTS");
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IvoService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ivoService =   retrofit.create(IvoService.class);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config.defaultDisplayImageOptions(defaultOptions);
        config.threadPoolSize(5);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.FIFO);
        config.writeDebugLogs(); // Remove for release app
        config.diskCacheExtraOptions(480, 320, null);

        // Initialize ImageLoader with configuration.
        this.imageLoader = ImageLoader.getInstance();
        imageLoader.init(config.build());
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
        Intent intent = new Intent(activity,PopupInfoActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        PopupInfoActivity.lstError = errors;
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


    public IvoService getApiServiceIntance() {
        return ivoService;
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

    public void setEntitySession(RolEntity entitySession) {
        this.entitySession = entitySession;
    }
    public RolEntity getEntitySession() {
        return this.entitySession;
    }

    public void applyFonts(View myLayout) {
        mFontApplyTask = new FontApplyTask(getApplicationContext(), myLayout);
        mFontApplyTask.execute((Void) null);
    }


    public class FragmentTask extends AsyncTask<Void, Void, Boolean> {

        private Fragment mFragment;


        FragmentTask(Fragment fragment) {
            mFragment = fragment;
        }
        @Override
        protected void onPreExecute() {
            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            }
            showProgress(true);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(mFragment!=null){

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


        }

        @Override
        protected void onCancelled() {
            mFragmentTask = null;
            showProgress(false);
        }
    }
}
