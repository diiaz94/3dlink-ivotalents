package com.threedlink.ivotalents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {
    private IvoTalentsApp mApp;

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
    }
}
