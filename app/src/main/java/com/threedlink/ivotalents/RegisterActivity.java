package com.threedlink.ivotalents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton register_button;
    private FrameLayout register_step_1;
    private FrameLayout register_step_2;
    private ImageButton artist_button;
    private ImageButton industry_button;
    private ImageButton provider_button;
    private ImageButton fan_button;

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
        fan_button = (ImageButton) findViewById(R.id.fan_btn);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_step_1.setVisibility(View.INVISIBLE);
                register_step_2.setVisibility(View.VISIBLE);
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
        fan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMainScreen();
            }
        });

    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
