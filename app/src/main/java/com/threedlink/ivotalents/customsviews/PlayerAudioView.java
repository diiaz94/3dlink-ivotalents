package com.threedlink.ivotalents.customsviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.utils.Util;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diiaz94 on 23-05-2017.
 */
public class PlayerAudioView  extends LinearLayout {

    private static final String TAG = "PlayerAudioView";
    View rootView;

    @Bind(R.id.record_reproductor)
    RelativeLayout recordReproductor;

    @Bind(R.id.container_filled_reproduced)
    LinearLayout containerFilledReproduced;
    @Bind(R.id.filled_reproduced)
    LinearLayout filledReproduced;

    @Bind(R.id.play_record_btn)
    Button playRecordBtn;
    @Bind(R.id.pause_record_btn)
    Button pauseRecordBtn;

    @Bind(R.id.record_name)
    TextView recordName;


    @Bind(R.id.record_duration)
    TextView recordDuration;


    MediaRecorder mRecorder;
    MediaPlayer mPlayer;
    File mFile;

    public PlayerAudioView(Context context) {
        super(context);
        init(null);
    }

    public PlayerAudioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(),R.layout.player_audio_view,this);
        init(attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AudioPlayer,
                0, 0);

        try {
            // mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            //mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
            //init(context);
        } finally {
            a.recycle();
        }
    }

    public PlayerAudioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        //rootView = inflate(context, R.layout.player_audio_view, this);
        //ButterKnife.bind(this,rootView);
        //initPlayer();
    }


    private void initPlayer() {
        mPlayer = new MediaPlayer();
        //  mPlayer.setOnCompletionListener(this);
        try {
            //mPlayer.setDataSource(mFile.getAbsolutePath());
            mPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }


    @OnClick(R.id.play_record_btn)
    public void play(View v) {
        mPlayer.start();
        //Toast.makeText(getActivity(), "duration: " + (player.getDuration()-player.getCurrentPosition()), Toast.LENGTH_LONG).show();
/*
        mCountDownTimer = new CountDownTimer(player.getDuration()-player.getCurrentPosition(), 1000) {

            public void onTick(long millisUntilFinished) {
                long totalDuration = player.getDuration();
                long currentDuration = totalDuration - millisUntilFinished;
                long totalWidth = containerFilledReproduced.getWidth();
                int calculateWidth = (int) (currentDuration * totalWidth / totalDuration);
                recordDuration.setText(Util.formatDuration((int) (totalDuration-currentDuration)));
                setWidthFilledReproduced(calculateWidth);


            }

            public void onFinish() {
                setWidthFilledReproduced(containerFilledReproduced.getWidth());
                recordDuration.setText("00:00");
                initRecordingAudioBtn.setEnabled(true);

            }

        };*/
        //mCountDownTimer.start();
        //mFrameAnimation.start();

        //Toast.makeText(getActivity(), "duration: " + player.getCurrentPosition(), Toast.LENGTH_LONG).show();
        playRecordBtn.setVisibility(View.GONE);
        pauseRecordBtn.setVisibility(View.VISIBLE);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                /*playRecordBtn.setVisibility(View.VISIBLE);
                pauseRecordBtn.setVisibility(View.GONE);
                setWidthFilledReproduced(0);
                recordDuration.setText(Util.formatDuration(mPlayer.getDuration()));
                */
            }

        });
    }
    public void setWidthFilledReproduced(int widthFilledReproduced) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(widthFilledReproduced,ViewGroup.LayoutParams.MATCH_PARENT);
            filledReproduced.setLayoutParams(param);
        }


}
