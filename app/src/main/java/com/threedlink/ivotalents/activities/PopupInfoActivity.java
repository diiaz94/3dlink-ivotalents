package com.threedlink.ivotalents.activities;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;

import java.util.ArrayList;

public class PopupInfoActivity extends AppCompatActivity {
    public static ArrayList<String> lstError;
    private TextView msgtext;
    private IvoTalentsApp mApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ((IvoTalentsApp) getApplicationContext());
        setContentView(R.layout.activity_popup_info);
        msgtext = (TextView) findViewById(R.id.msgtext);
        msgtext.setTypeface(FontApplyTask.getFont(getApplicationContext()));
        String formattedMsg = formatMsg();
        msgtext.setText(Html.fromHtml(formattedMsg));
        //The key argument here must match that used in the other activity

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3   ));

        ImageButton close = (ImageButton) findViewById(R.id.close_icon_popup);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                try {
                    finish();
                }catch (Exception e){
                    Log.e("NAME::","Error finalizando popup de mensajes");
                }
            }

        });


    }

    public String formatMsg(){

        String response="";
        if (this.lstError!=null){
            for (int i=0; i<this.lstError.size();i++){
                response += ("- "+this.lstError.get(i) + "<br/>");
            }
        }else{
            response = getResources().getString(R.string.error_global);
        }
        return response;
    }
}
