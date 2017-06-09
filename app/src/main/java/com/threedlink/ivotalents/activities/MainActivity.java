package com.threedlink.ivotalents.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.fragments.AdvancedSearch;
import com.threedlink.ivotalents.fragments.ArtistCasting;
import com.threedlink.ivotalents.fragments.Casting;
import com.threedlink.ivotalents.fragments.CastingDetail;
import com.threedlink.ivotalents.fragments.Chat;
import com.threedlink.ivotalents.fragments.CreateMessage;
import com.threedlink.ivotalents.fragments.DashboardArtist;
import com.threedlink.ivotalents.fragments.DashboardCasting;
import com.threedlink.ivotalents.fragments.HomeArtist;
import com.threedlink.ivotalents.fragments.HomeIndustry;
import com.threedlink.ivotalents.fragments.HomeProvider;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.fragments.Messages;
import com.threedlink.ivotalents.fragments.ProfileArtist;
import com.threedlink.ivotalents.fragments.ProfileIndustry;
import com.threedlink.ivotalents.fragments.ProfileProvider;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.fragments.ReceivedMessages;
import com.threedlink.ivotalents.fragments.SendMessages;
import com.threedlink.ivotalents.fragments.SessionManager;
import com.threedlink.ivotalents.fragments.SubmitAudition;
import com.threedlink.ivotalents.fragments.ParticipationsFinished;
import com.threedlink.ivotalents.fragments.ParticipationsStarted;
import com.threedlink.ivotalents.fragments.Participations;
import com.threedlink.ivotalents.fragments.IndustryCasting;
import com.threedlink.ivotalents.fragments.profiletabs.ProfileExperience;
import com.threedlink.ivotalents.fragments.profiletabs.ProfileAudioList;
import com.threedlink.ivotalents.fragments.profiletabs.ProfilePhotoGrid;
import com.threedlink.ivotalents.fragments.profiletabs.ProfileVideoGrid;
import com.threedlink.ivotalents.utils.Util;
import com.threedlink.ivotalents.utils.enums.Rol;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

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
        HomeIndustry.OnFragmentInteractionListener,
        HomeProvider.OnFragmentInteractionListener,
        HomeArtist.OnFragmentInteractionListener,
        Messages.OnFragmentInteractionListener,
        ReceivedMessages.OnFragmentInteractionListener,
        SendMessages.OnFragmentInteractionListener,
        CreateMessage.OnFragmentInteractionListener,
        Chat.OnFragmentInteractionListener,
        Casting.OnFragmentInteractionListener,
        CastingDetail.OnFragmentInteractionListener,
        SubmitAudition.OnFragmentInteractionListener,
        Participations.OnFragmentInteractionListener,
        ParticipationsStarted.OnFragmentInteractionListener,
        ParticipationsFinished.OnFragmentInteractionListener,
        ProfilePhotoGrid.OnFragmentInteractionListener,
        ProfileAudioList.OnFragmentInteractionListener,
        ProfileVideoGrid.OnFragmentInteractionListener,
        ProfileExperience.OnFragmentInteractionListener,
        IndustryCasting.OnFragmentInteractionListener,
        ArtistCasting.OnFragmentInteractionListener
        {
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
    private ImageView profile_photo;
    private View mMainContainerView;
    private View mProgressView;

    private final Util util = new Util();
    private TextView artist_name;
    private TextView artist_talent;
    private TextView artist_count_fans;
    private LinearLayout stars_puntuation;
    private TextView text_my_castings;

            @Override
    protected void onCreate(Bundle saveInstanceState) {
        Log.e("PEDRO", "PASO onCreate MainAct");
        super.onCreate(saveInstanceState);
        // Create global configuration and initialize ImageLoader with this config
        /*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        ...
        .build();
        ImageLoader.getInstance().init(config);*/
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_header);
        toolbar.setTitle("");
        profile_photo = (ImageView) findViewById(R.id.profile_photo);


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




        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        //btnLogout.setOnClickListener(this);

        ImageButton close_menu = (ImageButton) findViewById(R.id.close_icon_menu);
        close_menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });


        artist_name = (TextView) findViewById(R.id.artist_name);
        artist_talent = (TextView) findViewById(R.id.artist_talent);
        artist_count_fans = (TextView) findViewById(R.id.artist_count_fans);
        stars_puntuation = (LinearLayout) findViewById(R.id.stars_puntuation);
        TextView text_fans = (TextView) findViewById(R.id.text_fans);
        TextView text_view_my_profile = (TextView) findViewById(R.id.text_view_my_profile);
        TextView text_dashboard = (TextView) findViewById(R.id.text_dashboard);
        TextView text_receive_msg = (TextView) findViewById(R.id.text_receive_msg);
        TextView count_receive_msg = (TextView) findViewById(R.id.count_receive_msg);
        TextView text_send_msg = (TextView) findViewById(R.id.text_send_msg);
        text_my_castings = (TextView) findViewById(R.id.text_my_castings);
        TextView text_talent = (TextView) findViewById(R.id.text_talent);
        TextView text_industries = (TextView) findViewById(R.id.text_industries);
        TextView text_castings = (TextView) findViewById(R.id.text_castings);
        TextView text_providers = (TextView) findViewById(R.id.text_providers);
        TextView text_search = (TextView) findViewById(R.id.text_search);

        artist_name.setTypeface(FontApplyTask.getFontBold(getApplicationContext()));
        artist_talent.setTypeface(FontApplyTask.getFont(getApplicationContext()));
        artist_count_fans.setTypeface(FontApplyTask.getFontBold(getApplicationContext()));
        text_fans.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        text_view_my_profile.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        logout_text.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        text_dashboard.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        text_receive_msg.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        count_receive_msg.setTypeface(FontApplyTask.getFont(getApplicationContext()));
        text_send_msg.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        text_my_castings.setTypeface(FontApplyTask.getFontSemiBoldItalic(getApplicationContext()));
        text_talent.setTypeface(FontApplyTask.getFontLight(getApplicationContext()));
        text_industries.setTypeface(FontApplyTask.getFontLight(getApplicationContext()));
        text_castings.setTypeface(FontApplyTask.getFontLight(getApplicationContext()));
        text_providers.setTypeface(FontApplyTask.getFontLight(getApplicationContext()));
        text_search.setTypeface(FontApplyTask.getFontLight(getApplicationContext()));

        text_view_my_profile.setOnClickListener(this);

        FrameLayout opcion_my_dashboard = (FrameLayout) findViewById(R.id.opcion_my_dashboard);
        opcion_my_dashboard.setOnClickListener(this);

        FrameLayout opcion_mensajes_recibidos = (FrameLayout) findViewById(R.id.opcion_mensajes_recibidos);
        opcion_mensajes_recibidos.setOnClickListener(this);

        FrameLayout opcion_mensajes_enviados = (FrameLayout) findViewById(R.id.opcion_mensajes_enviados);
        opcion_mensajes_enviados.setOnClickListener(this);

        ImageButton redactar_btn = (ImageButton) findViewById(R.id.redactar_btn);
        redactar_btn.setOnClickListener(this);

        FrameLayout opcion_my_competitions = (FrameLayout) findViewById(R.id.opcion_my_competitions);
        opcion_my_competitions.setOnClickListener(this);

        FrameLayout opcion_search = (FrameLayout) findViewById(R.id.opcion_search);
        opcion_search.setOnClickListener(this);

        FrameLayout opcion_talents = (FrameLayout) findViewById(R.id.opcion_talents);
        opcion_talents.setOnClickListener(this);

        FrameLayout opcion_castings = (FrameLayout) findViewById(R.id.opcion_castings);
        opcion_castings.setOnClickListener(this);

        mMainContainerView = findViewById(R.id.content_main);
        mProgressView = findViewById(R.id.progress);
        mApp.setAsyncElementsUI(this,getSupportFragmentManager(),mProgressView,mProgressView,drawer);


        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(SessionManager.KEY_EMAIL);
        Call<RolEntity> userCall = mApp.getApiServiceIntance().general_information("", email);
        userCall.enqueue(new CustomRetrofitCallback<RolEntity>(this) {
            @Override
            protected void handleSuccess(Response response) {
                setScreenUserSesion((RolEntity)response.body());
            }

            @Override
            public void failure(Throwable error) {

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
        int id = v.getId();
        switch (id){
            case R.id.close_icon_menu:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.text_view_my_profile:
                mApp.loadFragment(getFragmentProfile());
                break;
            case R.id.opcion_my_dashboard:
                mApp.loadFragment(getFragmentDashboard());
                break;
            case R.id.opcion_mensajes_recibidos:
                android.app.Fragment fragmentMR = (android.app.Fragment) getFragmentManager().findFragmentByTag(Messages.class.getSimpleName());
                if (fragmentMR != null && fragmentMR.isVisible()) {
                    ViewPager viewPager = (ViewPager) fragmentMR.getView().findViewById(R.id.pager);
                    if (viewPager != null)
                        viewPager.setCurrentItem(0);
                } else {
                    mApp.loadFragment(Messages.newInstance("0", "param2"));
                }
                break;
            case R.id.opcion_mensajes_enviados:
                android.app.Fragment fragmentME = (android.app.Fragment) getFragmentManager().findFragmentByTag(Messages.class.getSimpleName());
                if (fragmentME != null && fragmentME.isVisible()) {
                    ViewPager viewPager = (ViewPager) fragmentME.getView().findViewById(R.id.pager);
                    if (viewPager != null)
                        viewPager.setCurrentItem(1);
                } else {
                    mApp.loadFragment(Messages.newInstance("1", "param2"));
                }
                break;
            case R.id.redactar_btn:
                mApp.loadFragment(CreateMessage.newInstance("param1", "param2"));
                break;
            case R.id.opcion_my_competitions:
                mApp.loadFragment(Participations.newInstance("1", "param2"));
                break;
            case R.id.opcion_search:
                mApp.loadFragment(AdvancedSearch.newInstance("param1", "param2"));
                break;
            case R.id.opcion_talents:
                mApp.loadFragment(DashboardArtist.newInstance("param1", "param2"));
                break;
            case R.id.opcion_castings:
                mApp.loadFragment(DashboardCasting.newInstance("1", "param2"));
                break;
         }
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

    public Fragment getFragmentProfile(){
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);
        Fragment fragment = null;

        if(name.equalsIgnoreCase("industry"))
            fragment = ProfileIndustry.newInstance(name,email);
        else if(name.equalsIgnoreCase("artist"))
            fragment = ProfileArtist.newInstance(name,email);
        else if(name.equalsIgnoreCase("provider"))
            fragment = ProfileProvider.newInstance(name,email);
        else
            fragment = ProfileArtist.newInstance(name,email);

        return fragment;
    }
    public Fragment getFragmentDashboard(){
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);
        Fragment fragment = null;

        if(name.equalsIgnoreCase("industry"))
            fragment = HomeIndustry.newInstance(name,email);
        else if(name.equalsIgnoreCase("artist"))
            fragment = HomeArtist.newInstance(name,email);
        else if(name.equalsIgnoreCase("provider"))
            fragment = HomeProvider.newInstance(name,email);
        else
            fragment = HomeArtist.newInstance(name,email);

        return fragment;
    }

    public void setScreenUserSesion(RolEntity entity){

        Resources res = getApplicationContext().getResources();
        mApp.setEntitySession(entity);
        artist_name.setText(entity.getName());
        artist_talent.setText(entity.getAbility());
        artist_count_fans.setText(String.valueOf(entity.getFansCount()));
        util.setStarsPuntuation(stars_puntuation,entity.getPuntuacion());

        try {
            Rol rol = Rol.valueOf(entity.getRole());

            switch (rol){
                case ARTISTA:
                    mApp.loadFragment(HomeArtist.newInstance("", ""));
                    profile_photo.setImageResource(R.drawable.profile_photo);
                    String[] abilities_to_auditions = res.getStringArray(R.array.abilities_to_auditions);
                    String[] abilities_to_castings= res.getStringArray(R.array.abilities_to_castings);
                    if(util.indexOf(abilities_to_auditions,entity.getAbility())!=-1 ){
                        text_my_castings.setText(getString(R.string.label_auditions_participations_option_text));
                    }
                    if(util.indexOf(abilities_to_castings,entity.getAbility()) !=-1 ){
                        text_my_castings.setText(getString(R.string.label_castings_participations_option_text));
                    }
                    break;
                case INDUSTRIA:
                    mApp.loadFragment(HomeIndustry.newInstance("", ""));
                    profile_photo.setImageResource(R.drawable.industry_photo);
                    text_my_castings.setText(getString(R.string.label_default_participations_option_text));
                    break;
                case PROVEEDOR:
                    mApp.loadFragment(HomeProvider.newInstance("", ""));
                    profile_photo.setImageResource(R.drawable.profile_photo_provider);
                    break;
            }
        }catch (Exception e){

        }

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
