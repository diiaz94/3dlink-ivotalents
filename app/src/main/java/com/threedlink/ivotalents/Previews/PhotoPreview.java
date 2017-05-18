package com.threedlink.ivotalents.previews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoPreview extends AppCompatActivity {
    private static final String TAG = "PhotoPreview";
    private final static String MEDIA_ACTION_ARG = "media_action_arg";
    private final static String FILE_PATH_ARG = "file_path_arg";

    @Bind(R.id.preview_photo)
    ImageView previewPhoto;


    private String previewFilePath;
    private ImageLoader imageLoader;

    public static Intent newIntentPhoto(Context context, String filePath) {
        return new Intent(context, PhotoPreview.class)
                .putExtra(FILE_PATH_ARG, filePath);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        ButterKnife.bind(this);
        Bundle args = getIntent().getExtras();
        previewFilePath = args.getString(FILE_PATH_ARG);
        imageLoader = ((IvoTalentsApp)getApplicationContext()).getImageLoader();
        imageLoader.displayImage(previewFilePath, previewPhoto);


    }
}
