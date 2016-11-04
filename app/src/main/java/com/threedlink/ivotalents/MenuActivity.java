package com.threedlink.ivotalents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    private IvoTalentsApp mApp;
    private TextView artist_name;
    private TextView artist_talent;
    private TextView artist_count_fans;
    private TextView text_fans;
    private TextView text_view_my_profile;
    private TextView text_dashboard;
    private TextView text_receive_msg;
    private TextView count_receive_msg;
    private TextView text_send_msg;
    private TextView text_my_castings;
    private TextView text_talent;
    private TextView text_industries;
    private TextView text_castings;
    private TextView text_providers;
    private TextView text_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mApp = ((IvoTalentsApp) getApplicationContext());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height));
        getWindow().setGravity(5);
        ImageButton close = (ImageButton) findViewById(R.id.close_icon_menu);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                try {
                    finish();
                }catch (Exception e){
                    Log.e("NAME::","Error finalizando menu");
                }
            }

        });

        artist_name = (TextView) findViewById(R.id.artist_name);
        artist_talent = (TextView) findViewById(R.id.artist_talent);
        artist_count_fans = (TextView) findViewById(R.id.artist_count_fans);
        text_fans = (TextView) findViewById(R.id.text_fans);
        text_view_my_profile = (TextView) findViewById(R.id.text_view_my_profile);
        text_dashboard = (TextView) findViewById(R.id.text_dashboard);
        text_receive_msg = (TextView) findViewById(R.id.text_receive_msg);
        count_receive_msg = (TextView) findViewById(R.id.count_receive_msg);
        text_send_msg = (TextView) findViewById(R.id.text_send_msg);
        text_my_castings = (TextView) findViewById(R.id.text_my_castings);
        text_talent = (TextView) findViewById(R.id.text_talent);
        text_industries = (TextView) findViewById(R.id.text_industries);
        text_castings = (TextView) findViewById(R.id.text_castings);
        text_providers = (TextView) findViewById(R.id.text_providers);
        text_search = (TextView) findViewById(R.id.text_search);

        artist_name.setTypeface(mApp.getFontBold());
        artist_talent.setTypeface(mApp.getFont());
        artist_count_fans.setTypeface(mApp.getFontBold());
        text_fans.setTypeface(mApp.getFontSemiBoldItalic());
        text_view_my_profile.setTypeface(mApp.getFontSemiBoldItalic());
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

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        overridePendingTransition(R.anim.left_out, R.anim.push_out_left);
    }
}
