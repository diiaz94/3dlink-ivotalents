package com.threedlink.ivotalents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by diiaz94 on 26-08-2016.
 */
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener,
        ProfileArtist.OnFragmentInteractionListener,
        ProfileIndustry.OnFragmentInteractionListener,
        AdvancedSearch.OnFragmentInteractionListener,
        ProfileProvider.OnFragmentInteractionListener,
        DashboardArtist.OnFragmentInteractionListener,
        DashboardCasting.OnFragmentInteractionListener,
        HomeIndustry.OnFragmentInteractionListener{
    DrawerLayout drawer;
    private static final String TAG = MainActivity.class.getSimpleName();
    // Session Manager Class
    SessionManager session;

    // Button Logout
    Button btnLogout;

    TextView lblName;
    TextView lblEmail;
    TextView logout_text;
    private IvoTalentsApp mApp;
    private ImageButton menu_icon;
    private ImageButton alert_icon;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        Log.e("PEDRO","PASO onCreate MainAct");
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_header);
        toolbar.setTitle("");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        logout_text = (TextView) findViewById(R.id.logout_text);
        logout_text.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
              logout(popupView);
            }
        });

        // Session class instance
        session = new SessionManager(getApplicationContext());
        mApp = ((IvoTalentsApp) getApplicationContext());
        lblName = (TextView) findViewById(R.id.lblName);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        setScreenUserSesion();

        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        //btnLogout.setOnClickListener(this);

        ImageButton close_menu = (ImageButton)  findViewById(R.id.close_icon_menu);
        close_menu.setOnClickListener(new View.OnClickListener() {

          public void onClick(View popupView) {
              if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                  drawer.closeDrawer(Gravity.RIGHT);
              } else {
                  drawer.openDrawer(Gravity.RIGHT);
              }
          }
        });


        TextView artist_name = (TextView) findViewById(R.id.artist_name);
        TextView artist_talent = (TextView) findViewById(R.id.artist_talent);
        TextView artist_count_fans = (TextView) findViewById(R.id.artist_count_fans);
        TextView text_fans = (TextView) findViewById(R.id.text_fans);
        TextView text_view_my_profile = (TextView) findViewById(R.id.text_view_my_profile);
        TextView text_dashboard = (TextView) findViewById(R.id.text_dashboard);
        TextView text_receive_msg = (TextView) findViewById(R.id.text_receive_msg);
        TextView count_receive_msg = (TextView) findViewById(R.id.count_receive_msg);
        TextView text_send_msg = (TextView) findViewById(R.id.text_send_msg);
        TextView text_my_castings = (TextView) findViewById(R.id.text_my_castings);
        TextView text_talent = (TextView) findViewById(R.id.text_talent);
        TextView text_industries = (TextView) findViewById(R.id.text_industries);
        TextView text_castings = (TextView) findViewById(R.id.text_castings);
        TextView text_providers = (TextView) findViewById(R.id.text_providers);
        TextView text_search = (TextView) findViewById(R.id.text_search);

        artist_name.setTypeface(mApp.getFontBold());
        artist_talent.setTypeface(mApp.getFont());
        artist_count_fans.setTypeface(mApp.getFontBold());
        text_fans.setTypeface(mApp.getFontSemiBoldItalic());
        text_view_my_profile.setTypeface(mApp.getFontSemiBoldItalic());
        logout_text.setTypeface(mApp.getFontSemiBoldItalic());
        text_dashboard.setTypeface(mApp.getFontSemiBoldItalic());
        text_receive_msg.setTypeface(mApp.getFontSemiBoldItalic());
        count_receive_msg.setTypeface(mApp.getFont());
        text_send_msg.setTypeface(mApp.getFontSemiBoldItalic());
        text_my_castings.setTypeface(mApp.getFontSemiBoldItalic());
        text_talent.setTypeface(mApp.getFontLight());
        text_industries.setTypeface(mApp.getFontLight());
        text_castings.setTypeface(mApp.getFontLight());
        text_providers.setTypeface(mApp.getFontLight());
        text_search.setTypeface(mApp.getFontLight());


        FrameLayout opcion_search = (FrameLayout) findViewById(R.id.opcion_search);
        opcion_search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                Fragment fragment = null;
                fragment = AdvancedSearch.newInstance("param1","param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack( "search" ).commit();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        FrameLayout opcion_talents = (FrameLayout) findViewById(R.id.opcion_talents);
        opcion_talents.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                Fragment fragment = null;
                fragment = DashboardArtist.newInstance("param1","param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack( "artists" ).commit();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        FrameLayout opcion_castings = (FrameLayout) findViewById(R.id.opcion_castings);
        opcion_castings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                Fragment fragment = null;
                fragment = DashboardCasting.newInstance("param1","param2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack( "castings" ).commit();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuRight){
            if (drawer.isDrawerOpen(Gravity.RIGHT)){
                drawer.closeDrawer(Gravity.RIGHT);
            }else{
                drawer.openDrawer(Gravity.RIGHT);
            }

            return true;
        }else{
            if (id == R.id.close_icon_menu){
                if (drawer.isDrawerOpen(Gravity.RIGHT)){
                    drawer.closeDrawer(Gravity.RIGHT);
                }else{
                    drawer.openDrawer(Gravity.RIGHT);
                }

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {

    }
    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logout(View view){
        // Clear the session data
        // This will clear all session data and
        // redirect user to LoginActivity
        boolean isLoggedWithFacebook = session.isLoggedWithFacebook();
        boolean isLoggedWithGoogle = session.isLoggedWithGoogle();
        session.logoutUser();
        if (isLoggedWithFacebook){
            Log.d(TAG,"Loggeado con facebook");
            LoginManager.getInstance().logOut();
        }
        if (isLoggedWithGoogle){
            Log.d(TAG,"Loggeado con google");
            Auth.GoogleSignInApi.signOut(mApp.getGoogleApiClient()).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                        }
                    });
        }
    }

    public void goToPrincipal(View view){
        Intent intent = new Intent(this,PrincipalActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    public void setScreenUserSesion(){



        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
        String email = user.get(SessionManager.KEY_EMAIL);
        // displaying user data
        //lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        //lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));

        Fragment fragment = null;

        if(name.equalsIgnoreCase("industry"))
            fragment = HomeIndustry.newInstance(name,email);
        else if(name.equalsIgnoreCase("artist"))
            fragment = ProfileArtist.newInstance(name,email);
        else if(name.equalsIgnoreCase("provider"))
        fragment = ProfileProvider.newInstance(name,email);
        else
            fragment = DashboardArtist.newInstance(name,email);


        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.text_my_castings2) {

        } else if (id == R.id.text_dashboard) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
