package com.threedlink.ivotalents.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.dtos.MediaResource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoVisorActivity extends AppCompatActivity {
    private static final String TAG = "PhotoVisorActivity" ;
    public static List<MediaResource> mList;
    public static int mIndex;
    private ImageLoader imageLoader;
    @Bind(R.id.close_visor)
    ImageView closeVisor;
    @Bind(R.id.visor_photo)
    ImageView visorPhoto;
    @Bind(R.id.right_photo_visor)
    RelativeLayout rightPhotoVisor;
    @Bind(R.id.left_photo_visor)
    RelativeLayout leftPhotoVisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_visor);
        ButterKnife.bind(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));
        imageLoader = ((IvoTalentsApp)getApplicationContext()).getImageLoader();
        imageLoader.displayImage(mList.get(mIndex).getPath(), visorPhoto);
        leftPhotoVisor.setVisibility(mIndex==0?View.INVISIBLE:View.VISIBLE);
        rightPhotoVisor.setVisibility(mIndex==mList.size()-1?View.INVISIBLE:View.VISIBLE);

        closeVisor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                try {
                    finish();
                }catch (Exception e){
                    Log.e(TAG,"Error finalizando visor de fotos");
                }
            }

        });
    }
    @OnClick(R.id.left_photo_visor)
    public void onLeftPhotoVisorClicked() {
        mIndex--;
        imageLoader.displayImage(mList.get(mIndex).getPath(), visorPhoto);
        leftPhotoVisor.setVisibility(mIndex==0?View.INVISIBLE:View.VISIBLE);
        rightPhotoVisor.setVisibility(mIndex==mList.size()-1?View.INVISIBLE:View.VISIBLE);
    }
    @OnClick(R.id.right_photo_visor)
    public void onRightPhotoVisorClicked() {
        mIndex++;
        imageLoader.displayImage(mList.get(mIndex).getPath(), visorPhoto);
        leftPhotoVisor.setVisibility(mIndex==0?View.INVISIBLE:View.VISIBLE);
        rightPhotoVisor.setVisibility(mIndex==mList.size()-1?View.INVISIBLE:View.VISIBLE);
    }

}
