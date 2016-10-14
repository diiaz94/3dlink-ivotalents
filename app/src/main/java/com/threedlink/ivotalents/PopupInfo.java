package com.threedlink.ivotalents;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PopupInfo extends AppCompatActivity {

    private TextView msgtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_info);
        msgtext = (TextView) findViewById(R.id.msgtext);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            //ArrayList<String> errors = (ArrayList<String> ) getIntent().getBundleExtra("errors");;
            ArrayList<String> errors = getIntent().getExtras().getStringArrayList("errors");
            ArrayList<String> errorsa = getIntent().getExtras().getStringArrayList("data");
            errors = (ArrayList<String>) extras.getSerializable("errors");;
            String errorsarr[] = extras.getStringArray("errors");;
            Parcelable errorspar[] = (Parcelable[]) extras.getParcelableArray("errors");;

            Bundle b = getIntent().getExtras().getBundle("errors");

            String formattedMsg = formatMsg(null);
            msgtext.setText(Html.fromHtml(formattedMsg));
            //The key argument here must match that used in the other activity
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.4   ));

        ImageButton close = (ImageButton) findViewById(R.id.close_icon_popup);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                finish();
            }

        });


    }

    public String formatMsg(ArrayList<String> errorsArray){

        String response="";
        if (errorsArray!=null){
            for (int i=0; i<errorsArray.size();i++){
                response += errorsArray.get(i) + "<br/>";
            }
        }else{
            response = getResources().getString(R.string.error_global);
        }
        return response;
    }
}
