package com.threedlink.ivotalents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class RememberPopUpActivity extends AppCompatActivity {
    private IvoTalentsApp mApp;
    private ImageButton close;
    private ImageButton sent_btn;
    private EditText user;
    private EditText email;
    private TextView lbluser;
    private TextView lblemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_remember);
        mApp = ((IvoTalentsApp) getApplicationContext());
        close = (ImageButton) findViewById(R.id.close_icon);
        sent_btn = (ImageButton) findViewById(R.id.sent_btn);
        user = (EditText) findViewById(R.id.user);
        email = (EditText) findViewById(R.id.email);
        lbluser = (TextView) findViewById(R.id.user_label);
        lblemail = (TextView) findViewById(R.id.email_label);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width),(int)(height));

        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                finish();
            }

        });

        sent_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                String user_text = user.getText().toString();
                String email_text = email.getText().toString();
                boolean cancel = false;
                ArrayList<String> errors= new ArrayList<String>();

                if (TextUtils.isEmpty(user_text)){
                    errors.add(getString(R.string.error_user_required));
                    cancel = true;
                }

                if (TextUtils.isEmpty(email_text)){
                    errors.add(getString(R.string.error_email_required));
                    cancel = true;
                }else{
                    if(!email.getText().toString().contains("@")){
                        errors.add(getString(R.string.error_invalid_email));
                        cancel = true;
                    }
                }


                if(cancel){
                    mApp.showError(RememberPopUpActivity.this,errors);
                }else{
                    //Enviar Datos
                    finish();
                }

            }

        });
        user.setTypeface(mApp.getFont());
        email.setTypeface(mApp.getFont());
        lbluser.setTypeface(mApp.getFont());
        lblemail.setTypeface(mApp.getFont());
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
